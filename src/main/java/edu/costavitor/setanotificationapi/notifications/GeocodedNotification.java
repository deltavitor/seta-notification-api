package edu.costavitor.setanotificationapi.notifications;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodedNotification {

    private String numeroNotificacao;

    private Double latitude;

    private Double longitude;
}
