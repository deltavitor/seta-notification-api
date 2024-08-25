package edu.costavitor.setanotificationapi.notifications;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> findAllNotifications() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.findAllNotifications());
    }

    @GetMapping(value = "/notifications", params = {"returnGeocodedNotifications"})
    public ResponseEntity<List<GeocodedNotification>> findAllGeocodedNotifications(@RequestParam("returnGeocodedNotifications") Boolean returnGeocodedNotifications) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.findAllGeocodedNotifications());
    }

    @PostMapping("/notifications")
    public ResponseEntity<List<Notification>> addNotificationsFromDbfFile(@RequestBody String filePath) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.addNotificationsFromDbfFile(filePath));
    }
}
