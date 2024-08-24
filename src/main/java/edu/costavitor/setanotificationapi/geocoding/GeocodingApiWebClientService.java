package edu.costavitor.setanotificationapi.geocoding;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
@Slf4j
public class GeocodingApiWebClientService {

    @Qualifier("geocodingApiWebClient")
    private WebClient geocodingApiWebClient;

    public Location getLocationByAddress(String address) {

        GeocodeResponse response = geocodingApiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/json")
                        .queryParam("address", address)
                        .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class)
                .block();

        // TODO improve error handling
        if (response != null)
            return response.getResults().getFirst().getGeometry().getLocation();
        return null;
    }
}
