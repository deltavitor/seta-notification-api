package edu.costavitor.setanotificationapi.geocoding;

import edu.costavitor.setanotificationapi.notifications.NotificationAddress;
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

    public Geocode getLocationByAddress(List<NotificationAddress> addresses) {

        List<Geocode> allResults = new ArrayList<>();
        for (NotificationAddress address : addresses) {
            GeocodeResponse response = geocodingApiWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/json")
                            .queryParam("address", address.toString())
                            .build())
                    .retrieve()
                    .bodyToMono(GeocodeResponse.class)
                    .block();

            // TODO improve error handling
            if (response == null) return null;

            List<Geocode> results = response.getResults();
            results = results.stream()
                    .filter(geocode -> isValidGeocode(geocode, address))
                    .toList();

            if (results.size() == 1) return results.getFirst();
            else allResults.addAll(results);
        }

        // Return the first ROOFTOP result (ideal) or RANGE_INTERPOLATED
        return allResults.stream()
                .filter(geocode -> "ROOFTOP".equals(geocode.getGeometry().getLocationType())).findFirst()
                .or(() -> allResults.stream().filter(geocode -> "RANGE_INTERPOLATED".equals(geocode.getGeometry().getLocationType())).findFirst())
                // If none of those exist, return the geocode with the shortest placeId instead,
                // which according to Google represents the most accurate result
                .or(() -> allResults.stream().min(Comparator.comparingInt(geocode -> geocode.getPlaceId().length())))
                .orElse(allResults.isEmpty() ? null : allResults.getFirst());
    }

    private boolean isValidGeocode(Geocode geocode, NotificationAddress address) {
        // Check to see if the Municipio from the Geocode is the same from the NotificationAddress, because
        // if it isn't, the Geocode is invalid
        for (AddressComponent addressComponent : geocode.getAddressComponents())
            if (addressComponent.getTypes().contains("administrative_area_level_2"))
                return addressComponent.getLongName().equalsIgnoreCase(address.getMunicipio());

        return false;
    }
}
