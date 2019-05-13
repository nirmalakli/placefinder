package org.travel.nirmal.placefinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.travel.nirmal.placefinder.SearchProvider;

@Component
public class PlaceSearchProviderFactory {

    @Autowired
    private final GooglePlacesSearchProvider googlePlacesSearchProvider;

    @Autowired
    private final FourSquarePlacesSearchProvider fourSquarePlacesSearchProvider;

    public PlaceSearchProviderFactory(GooglePlacesSearchProvider googlePlacesSearchProvider, FourSquarePlacesSearchProvider fourSquarePlacesSearchProvider) {
        this.googlePlacesSearchProvider = googlePlacesSearchProvider;
        this.fourSquarePlacesSearchProvider = fourSquarePlacesSearchProvider;
    }

    public PlacesSearchProvider createSearchProvider(PlaceSearchCriteria criteria) {
        SearchProvider searchProvider = criteria.getSearchProvider();
        switch(searchProvider) {
            case GOOGLE:
                return googlePlacesSearchProvider;
            case FOUR_SQUARE:
                return fourSquarePlacesSearchProvider;
            default:
                throw new IllegalArgumentException("searchProvider: " + searchProvider + " is not supported as of now");
        }
    }

}
