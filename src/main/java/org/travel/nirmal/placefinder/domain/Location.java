package org.travel.nirmal.placefinder.domain;

public class Location {
    private final String locationDescription;

    public Location(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getDescription() {
        return locationDescription;
    }
}
