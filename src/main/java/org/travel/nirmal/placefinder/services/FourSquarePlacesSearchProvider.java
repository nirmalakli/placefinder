package org.travel.nirmal.placefinder.services;

import org.springframework.stereotype.Service;
import org.travel.nirmal.placefinder.domain.Place;

import java.util.List;

@Service
public class FourSquarePlacesSearchProvider implements PlacesSearchProvider {

    @Override
    public List<Place> searchPlaces(PlaceSearchCriteria placeSearchCriteria) {
        throw new UnsupportedOperationException("This is yet to be implemented!!");
    }
}
