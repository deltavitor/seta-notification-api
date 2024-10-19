package edu.costavitor.setanotificationapi.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressComponent {

    @JsonProperty("long_name")
    private String longName;

    private List<String> types;
}
