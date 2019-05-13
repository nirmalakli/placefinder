package org.travel.nirmal.placefinder.services;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.travel.nirmal.placefinder.domain.Coordinate;
import org.travel.nirmal.placefinder.domain.Location;
import org.travel.nirmal.placefinder.domain.Place;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GooglePlacesSearchProviderTest {

    @Mock
    DataGateway dataGateway;

    private GooglePlacesSearchProvider googlePlacesSearchProvider;


    private Place place1 = new Place("Mansi's Golden Dragon", "restaurant", new Coordinate(18.5621065,73.8333674));
    private Place place2 = new Place("Sourabh Family Garden & Restaurant", "restaurant", new Coordinate(18.5677659,73.8388853));
    private Place place3 = new Place("Kohinoor Chinese & Biryani House", "restaurant", new Coordinate(18.575041,73.824778));

    @Before
    public void setUp() throws Exception {
        googlePlacesSearchProvider = new GooglePlacesSearchProvider(dataGateway, "mock-api-key");
    }

    @Test
    public void parsesPlaceId() {
        when(dataGateway.get(anyString())).thenReturn(readFromFile("place_id.json"));
        Optional<String> placeId = googlePlacesSearchProvider.findPlaceId(new Location("aundh"));
        assertThat(placeId.isPresent(), is(Boolean.TRUE));
        assertThat(placeId.get(), is("ChIJAaTjVle_wjsRtAZFGWxqlT0"));
    }

    @Test
    public void findsLocationCoordinates() {
        when(dataGateway.get(anyString())).thenReturn(readFromFile("place_coordinates.json"));
        Optional<Coordinate> coordinate = googlePlacesSearchProvider.locate("ChIJAaTjVle_wjsRtAZFGWxqlT0");
        assertThat(coordinate.isPresent(), is(Boolean.TRUE));
        assertThat(coordinate.get(), is(new Coordinate(18.562868,73.8365305)));
    }

    @Test
    public void findsPlacesOfInterest() {
        when(dataGateway.get(anyString())).thenReturn(readFromFile("places_results.json"));
        List<Place> coordinate = googlePlacesSearchProvider.findPlaces(new Coordinate(18.562868,73.8365305), "", 1);
        assertThat(coordinate, contains(place1, place2, place3));
    }

    private String readFromFile(String fileName) {
        String filePath = "google-api/" + fileName;
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        try {
            List<String> lines = IOUtils.readLines(stream, Charset.forName("UTF-8"));
            return String.join("\n", lines);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read from file: " + filePath, e);
        }
    }
}