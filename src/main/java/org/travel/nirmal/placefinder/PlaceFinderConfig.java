package org.travel.nirmal.placefinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class PlaceFinderConfig {


    @Autowired
    private Environment environment;

    @Bean
    public String googleApiKey() {
        String googleApiKey = environment.getProperty("google-api-key");
        if(StringUtils.isEmpty(googleApiKey)) {
            throw new IllegalStateException("Please provide a property with name = google-api-key");
        }
        return googleApiKey;
    }

    @Bean
    public SearchProvider defaultSearchProvider() {
        String searchProviderStr = environment.getProperty("default-search-provider");
        if(StringUtils.isEmpty(searchProviderStr)) {
            throw new IllegalStateException("Please provide a property with name = default-search-provider");
        }

        return SearchProvider.valueOf(searchProviderStr);
    }

    @Bean
    public Integer defaultSearchRadius() {
        String defaultSearchRadiusStr = environment.getProperty("default-search-radius", "0");
        return Integer.valueOf(defaultSearchRadiusStr);
    }
}
