package org.travel.nirmal.placefinder.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.travel.nirmal.placefinder.domain.Location;
import org.travel.nirmal.placefinder.domain.Place;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
public class PlacesControllerIntegrationTest {

    @Autowired
    private PlacesController controller;

    @Value("${default-search-radius:1500}")
    private Integer defaultSearchRadius;

    @Test
    public void canFetchPlacesInformation() {
        List<Place> chineseRestaurants = controller.findPlacesLocatedWithinADistance(new Location("aundh"), "chinese restaurant", defaultSearchRadius, null);
        assertThat(chineseRestaurants.size(), is(20));
    }
}