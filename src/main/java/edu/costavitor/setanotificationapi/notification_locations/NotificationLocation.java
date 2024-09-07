package edu.costavitor.setanotificationapi.notification_locations;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.costavitor.setanotificationapi.notifications.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationLocation {

    private String numeroNotificationLocation;

    private Double latitude;

    private Double longitude;

    private String locationType;

    private String formattedAddress;

    // TODO maybe create a NotificationSummary class?
    private List<Notification> notifications;
}
