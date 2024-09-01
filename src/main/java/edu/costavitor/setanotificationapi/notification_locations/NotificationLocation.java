package edu.costavitor.setanotificationapi.notification_locations;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationLocation {

    private String numeroNotificationLocation;

    private Double latitude;

    private Double longitude;

    private String locationType;

    private String formattedAddress;
}
