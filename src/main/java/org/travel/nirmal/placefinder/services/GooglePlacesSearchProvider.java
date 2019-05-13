package org.travel.nirmal.placefinder.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.travel.nirmal.placefinder.domain.Coordinate;
import org.travel.nirmal.placefinder.domain.Location;
import org.travel.nirmal.placefinder.domain.Place;
import org.travel.nirmal.placefinder.googleapi.dto.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GooglePlacesSearchProvider implements PlacesSearchProvider {

    private static final Logger logger = LoggerFactory.getLogger(GooglePlacesSearchProvider.class);

    private final String URL_PLACE_ID_FINDER = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=%s&input=%s&inputtype=textquery";
    private final String URL_PLACE_DETAILS_FINDER = "https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=%s";
    private final String URL_PLACE_FINDER = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&type=restaurant&keyword=%s&key=%s&radius=%d";

    @Autowired
    private final DataGateway gateway;

    @Autowired
    @Qualifier("googleApiKey")
    private final String apiKey;

    public GooglePlacesSearchProvider(DataGateway gateway, String apiKey) {
        this.gateway = gateway;
        this.apiKey = apiKey;
    }

    @Override
    public List<Place> searchPlaces(PlaceSearchCriteria placeSearchCriteria) {
        return searchPlaces(placeSearchCriteria.getLocation(), placeSearchCriteria.getSearchText(), placeSearchCriteria.getDistance());
    }

    private List<Place> searchPlaces(Location location, String searchText, Integer defaultSearchRadius) {
        Optional<String> placeIdOpt = findPlaceId(location);
        if(placeIdOpt.isPresent()) {
            String placeId = placeIdOpt.get();
            logger.info("Place Id of {} is {}", location.getDescription(), placeId);
            Optional<Coordinate> coordinatesOpt = locate(placeId);
            if(coordinatesOpt.isPresent()) {
                Coordinate coordinates = coordinatesOpt.get();
                logger.info("Co-oridinates of {} is {}", location.getDescription(), coordinates);
                return findPlaces(coordinates, searchText, defaultSearchRadius);
            }
        }
        return Collections.emptyList();
    }

    Optional<String> findPlaceId(Location location) {
        String url = String.format(URL_PLACE_ID_FINDER,
                apiKey,
                location.getDescription());
        String data = gateway.get(url);
        return parsePlaceId(data);
    }

    private Optional<String> parsePlaceId(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PlaceIdResult placeIdResult = objectMapper.readValue(data, PlaceIdResult.class);
            List<Candidate> candidates = placeIdResult.getCandidates();
            if(!CollectionUtils.isEmpty(candidates)) {
                return Optional.ofNullable(candidates.get(0))
                        .map(Candidate::getPlaceId);
            }
        } catch (IOException e) {
            logger.error("Error parsing placeId", e);
        }
        return Optional.empty();
    }

    Optional<Coordinate> locate(String placeId) {
        String url = String.format(URL_PLACE_DETAILS_FINDER, placeId, apiKey);
        String data = gateway.get(url);
        return parseCoordinates(data);
    }

    private Optional<Coordinate> parseCoordinates(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(data);
            JsonNode locationNode = rootNode.path("result").path("geometry").path("location");
            double latitude = locationNode.path("lat").asDouble();
            double longitude = locationNode.path("lng").asDouble();
            return Optional.of(new Coordinate(latitude, longitude));
        } catch (IOException e) {
            logger.error("Error parsing place co-ordinates", e);
        }

        return Optional.empty();
    }

    List<Place> findPlaces(Coordinate coordinates, String searchText, int radius) {
        String url = String.format(URL_PLACE_FINDER, coordinates.getLatitude(), coordinates.getLongitude(), searchText, apiKey, radius);
        String data = gateway.get(url);
        return parsePlaces(data);
    }

    private List<Place> parsePlaces(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SearchResults searchResults = objectMapper.readValue(data, SearchResults.class);
            List<Result> results = searchResults.getResults();
            return results.stream()
                    .map(result -> convertToPlace(result))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error parsing place search results", e);
        }

        return Collections.emptyList();
    }

    private Place convertToPlace(Result result) {
        Geometry geometry = result.getGeometry();
        Double latitude = geometry.getLocation().getLat();
        Double longitude = geometry.getLocation().getLng();
        Coordinate coordinate = new Coordinate(latitude, longitude);
        String name = result.getName();
        String type = result.getTypes().get(0);
        return new Place(name, type, coordinate);
    }
}
