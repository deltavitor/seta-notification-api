package edu.costavitor.setanotificationapi.notification_locations;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class NotificationLocationController {

    private NotificationLocationService notificationLocationService;

    @GetMapping("/notification-locations")
    public ResponseEntity<List<NotificationLocation>> findAllNotificationLocations(@CookieValue(name = "setaSessionId") String userId) {
        // Because this is the entry point, this is where we set the cookie
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            ResponseCookie cookie = ResponseCookie.from("setaSessionId", userId)
                    .path("/")
                    .httpOnly(true)
                    .build();

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", cookie.toString())
                    .body(notificationLocationService.findAllNotificationLocations(userId));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationLocationService.findAllNotificationLocations(userId));
    }

    @DeleteMapping("notification-locations")
    public ResponseEntity<Void> deleteNotificationLocations(@CookieValue(name = "setaSessionId") String userId) {
        notificationLocationService.deleteAllNotificationLocations(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
