package edu.costavitor.setanotificationapi.geocoding;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GeocodingApiWebClientService {

    @Qualifier("geocodingApiWebClient")
    private WebClient geocodingApiWebClient;

    public Geocode getLocationByAddress(List<String> addresses) {

        List<Geocode> allResults = new ArrayList<>();
        for (String address : addresses) {
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

            if (results.size() == 1) return results.getFirst();
            else allResults.addAll(results);
        }

        // Return the first ROOFTOP result (ideal) or RANGE_INTERPOLATED
        return allResults.stream().filter(geocode -> "ROOFTOP".equals(geocode.getGeometry().getLocationType())).findFirst()
                .or(() -> allResults.stream().filter(geocode -> "RANGE_INTERPOLATED".equals(geocode.getGeometry().getLocationType())).findFirst())
                // If none of those exist, return the geocode with the shortest placeId instead,
                // which according to Google represents the most accurate result
                .or(() -> allResults.stream().min(Comparator.comparingInt(geocode -> geocode.getPlaceId().length())))
                .orElse(allResults.isEmpty() ? null : allResults.getFirst());
    }
}
