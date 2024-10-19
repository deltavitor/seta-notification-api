package edu.costavitor.setanotificationapi.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Geocode {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    private Geometry geometry;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    @JsonProperty("partial_match")
    private Boolean partialMatch;

    @JsonProperty("place_id")
    private String placeId;
}
