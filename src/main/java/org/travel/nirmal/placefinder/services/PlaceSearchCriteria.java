package org.travel.nirmal.placefinder.services;

import org.travel.nirmal.placefinder.SearchProvider;
import org.travel.nirmal.placefinder.domain.Location;

import java.util.Objects;

public class PlaceSearchCriteria {
    private final Location location;
    private final String searchText;
    private final Integer distance;
    private final SearchProvider searchProvider;

    public PlaceSearchCriteria(Location location, String searchText, Integer distance) {
        this(location, searchText, distance, null);
    }

    public PlaceSearchCriteria(Location location, String searchText, Integer distance, SearchProvider searchProvider) {
        this.location = location;
        this.searchText = searchText;
        this.distance = distance;
        this.searchProvider = searchProvider;
    }

    public Location getLocation() {
        return location;
    }

    public String getSearchText() {
        return searchText;
    }

    public Integer getDistance() {
        return distance;
    }

    public SearchProvider getSearchProvider() {
        return searchProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceSearchCriteria that = (PlaceSearchCriteria) o;
        return Objects.equals(location, that.location) &&
                Objects.equals(searchText, that.searchText) &&
                Objects.equals(distance, that.distance) &&
                searchProvider == that.searchProvider;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, searchText, distance, searchProvider);
    }
}
