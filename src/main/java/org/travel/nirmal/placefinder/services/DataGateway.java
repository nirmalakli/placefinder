package org.travel.nirmal.placefinder.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataGateway {

    private static final Logger logger = LoggerFactory.getLogger(DataGateway.class);

    public String get(String url) {
        logger.info("Fetching url {} - " + url);
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new IllegalStateException(
                    String.format("Unable to fetch data : %n\tResponse status: %s%n\tResponse body: %s",
                        responseEntity.getStatusCode(), responseEntity.getBody()));
        }
    }

}
