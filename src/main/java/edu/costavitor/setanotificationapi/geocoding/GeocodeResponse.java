package edu.costavitor.setanotificationapi.geocoding;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeocodeResponse {

    private List<Geocode> results;
}


