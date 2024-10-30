package edu.costavitor.setanotificationapi.notification_locations;

import edu.costavitor.setanotificationapi.common.DBFUtils;
import edu.costavitor.setanotificationapi.notifications.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationLocationController {

    private NotificationLocationService notificationLocationService;

    private NotificationService notificationService;

    @GetMapping("/notification-locations")
    public ResponseEntity<List<NotificationLocation>> findAllNotificationLocations() throws IOException {

        // Temporary: use a static file to test the system with end users. If there are no notifications available,
        // automatically "upload" from the static file
        List<NotificationLocation> notificationLocations = notificationLocationService.findAllNotificationLocations();
        if (!notificationLocations.isEmpty())
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notificationLocationService.findAllNotificationLocations());

        notificationService.addNotificationsFromDbfFile(DBFUtils.getStaticNotificationsDbfAsMultipart());
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationLocationService.findAllNotificationLocations());
    }

    @DeleteMapping("notification-locations")
    public ResponseEntity<Void> deleteNotificationLocations() {
        notificationLocationService.deleteAllNotificationLocations();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
