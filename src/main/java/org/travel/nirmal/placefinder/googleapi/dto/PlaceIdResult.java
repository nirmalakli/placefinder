package org.travel.nirmal.placefinder.googleapi.dto;

import org.travel.nirmal.placefinder.googleapi.dto.Candidate;

import java.util.ArrayList;
import java.util.List;

public class PlaceIdResult {
    private List<Candidate> candidates = new ArrayList<>();
    private String status;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

