package edu.costavitor.setanotificationapi.notification_locations;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationLocationController {

    private NotificationLocationService notificationLocationService;

    @GetMapping("/notification-locations")
    public ResponseEntity<List<NotificationLocation>> findAllNotificationLocations() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationLocationService.findAllNotificationLocations());
    }
}
