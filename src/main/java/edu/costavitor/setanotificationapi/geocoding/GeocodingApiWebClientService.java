package edu.costavitor.setanotificationapi.geocoding;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GeocodingApiWebClientService {

    @Qualifier("geocodingApiWebClient")
    private WebClient geocodingApiWebClient;

    public Geocode getLocationByAddress(String address) {

        GeocodeResponse response = geocodingApiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/json")
                        .queryParam("address", address)
                        .build())
                .retrieve()
                .bodyToMono(GeocodeResponse.class)
                .block();

        // TODO improve error handling
        if (response == null) return null;

        List<Geocode> results = response.getResults();
        // Return the first ROOFTOP result (ideal) or RANGE_INTERPOLATED
        return results.stream().filter(geocode -> "ROOFTOP".equals(geocode.getGeometry().getLocationType())).findFirst()
                .or(() -> results.stream().filter(geocode -> "RANGE_INTERPOLATED".equals(geocode.getGeometry().getLocationType())).findFirst())
                // If none of those exist, return the geocode with the shortest placeId instead,
                // which according to Google represents the most accurate result
                .or(() -> results.stream().min(Comparator.comparingInt(geocode -> geocode.getPlaceId().length())))
                .orElse(results.isEmpty() ? null : results.getFirst());
    }
}
