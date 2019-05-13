package org.travel.nirmal.placefinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class PlaceFinderTestConfig {

    @Autowired
    private Environment environment;

    @Bean
    public String apiKey() {
        String googleApiKey = environment.getProperty("google-api-key");
        if(StringUtils.isEmpty(googleApiKey)) {
            throw new IllegalStateException("Please provide a system property with name = google-api-key");
        }
        return googleApiKey;
    }

    @Bean
    public Integer defaultSearchRadius() {
        String defaultSearchRadiusStr = environment.getProperty("default-search-radius", "0");
        return Integer.valueOf(defaultSearchRadiusStr);
    }
}
