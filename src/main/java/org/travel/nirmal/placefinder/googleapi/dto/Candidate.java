package org.travel.nirmal.placefinder.googleapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Candidate {
    @JsonAlias("place_id")
    private String placeId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
