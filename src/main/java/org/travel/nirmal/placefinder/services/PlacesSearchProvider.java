package org.travel.nirmal.placefinder.services;

import org.travel.nirmal.placefinder.domain.Place;

import java.util.List;

public interface PlacesSearchProvider {
    List<Place> searchPlaces(PlaceSearchCriteria placeSearchCriteria);
}
