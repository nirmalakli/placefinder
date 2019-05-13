package org.travel.nirmal.placefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.travel.nirmal.placefinder.SearchProvider;
import org.travel.nirmal.placefinder.domain.Location;
import org.travel.nirmal.placefinder.domain.Place;
import org.travel.nirmal.placefinder.services.PlaceFinderService;
import org.travel.nirmal.placefinder.services.PlaceSearchCriteria;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlacesController {

    @Autowired
    private final PlaceFinderService service;

    public PlacesController(PlaceFinderService service) {
        this.service = service;
    }

    @GetMapping(value = "/{location}/{searchText}")
    public List<Place> findPlacesLocatedWithinADistance(
            @PathVariable(value = "location") Location location,
            @PathVariable(value = "searchText") String searchText,
            @RequestParam(value = "distance", required = false) Integer distance,
            @RequestParam(value = "searchProvider", required = false) SearchProvider searchProvider){

        return service.findPlacesWithinLocation(new PlaceSearchCriteria(location, searchText, distance, searchProvider));
    }
}
