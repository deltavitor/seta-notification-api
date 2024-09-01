package edu.costavitor.setanotificationapi.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geometry {

    private Location location;

    @JsonProperty("location_type")
    private String locationType;
}
