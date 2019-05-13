package org.travel.nirmal.placefinder.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.travel.nirmal.placefinder.domain.Coordinate;
import org.travel.nirmal.placefinder.domain.Location;
import org.travel.nirmal.placefinder.domain.Place;
import org.travel.nirmal.placefinder.services.PlaceFinderService;
import org.travel.nirmal.placefinder.services.PlaceSearchCriteria;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class PlacesControllerTest {

    private Place place1;
    private Place place2;

    @Mock
    private PlaceFinderService service;
    private Location location;

    @Before
    public void setUp() {
        place1 = new Place("kimling", "restaurant", new Coordinate(18.5,73.84));
        place2 = new Place("mainland china", "restaurant", new Coordinate(18.4,73.83));
        location = new Location("Aundh Pune");

        PlaceSearchCriteria searchCriteria = new PlaceSearchCriteria(location, "chinese restaurant", Integer.valueOf(500), null);
        when(service.findPlacesWithinLocation(searchCriteria)).thenReturn(Arrays.asList(place1, place2));
    }

    @Test
    public void shouldBeAbleToSearchForPlacesLocatedWithinASpecificDistance() {
        PlacesController controller = new PlacesController(service);
        List<Place> places = controller.findPlacesLocatedWithinADistance(location, "chinese restaurant", Integer.valueOf(500), null);
        assertThat(places, contains(place1, place2));
    }

}