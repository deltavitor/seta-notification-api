package edu.costavitor.setanotificationapi.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geocode {

    private Geometry geometry;

    @JsonProperty("formatted_address")
    private String formattedAddress;
}
