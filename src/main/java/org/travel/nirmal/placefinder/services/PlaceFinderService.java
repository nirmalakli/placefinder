package org.travel.nirmal.placefinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.travel.nirmal.placefinder.SearchProvider;
import org.travel.nirmal.placefinder.domain.Place;

import java.util.List;

import static org.apache.commons.lang.ObjectUtils.defaultIfNull;

@Service
public class PlaceFinderService {

    @Autowired
    private final PlaceSearchProviderFactory searchProviderFactory;

    @Value("${default-search-radius:1500}")
    private final Integer defaultSearchRadius;

    @Autowired
    private final SearchProvider defaultSearchProvider;

    public PlaceFinderService(PlaceSearchProviderFactory searchProviderFactory, Integer defaultSearchRadius, SearchProvider defaultSearchProvider) {
        this.searchProviderFactory = searchProviderFactory;
        this.defaultSearchRadius = defaultSearchRadius;
        this.defaultSearchProvider = defaultSearchProvider;
    }

    public List<Place> findPlacesWithinLocation(PlaceSearchCriteria searchCriteria) {
        PlaceSearchCriteria updatedCriteria = populateCriteriaWithDefaults(searchCriteria);
        PlacesSearchProvider searchProvider = searchProviderFactory.createSearchProvider(updatedCriteria);
        return searchProvider.searchPlaces(updatedCriteria);
    }

    private PlaceSearchCriteria populateCriteriaWithDefaults(PlaceSearchCriteria searchCriteria) {
        Integer searchRadius = (Integer) defaultIfNull(searchCriteria.getDistance(), defaultSearchRadius);
        SearchProvider searchProvider = (SearchProvider) defaultIfNull(searchCriteria.getSearchProvider(), defaultSearchProvider);
        return new PlaceSearchCriteria(searchCriteria.getLocation(), searchCriteria.getSearchText(), searchRadius, searchProvider);
    }
}
