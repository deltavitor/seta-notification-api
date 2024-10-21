package edu.costavitor.setanotificationapi.notifications;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> findAllNotifications(@CookieValue(name = "setaSessionId") String userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.findAllNotifications(userId));
    }

    @GetMapping(value = "/notifications", params = {"returnUnmappedNotificationsOnly"})
    public ResponseEntity<List<Notification>> findAllUnmappedNotifications(@RequestParam("returnUnmappedNotificationsOnly") String returnUnmappedNotificationsOnly, @CookieValue(name = "setaSessionId") String userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.findAllUnmappedNotifications(userId));
    }

    @GetMapping("/notifications/{numeroNotificacao}")
    public Notification getNotificationByNumeroNotificacao(@PathVariable("numeroNotificacao") String numeroNotificacao, @CookieValue(name = "setaSessionId") String userId) {
        return notificationService.getNotificationByNumeroNotificacao(numeroNotificacao, userId);
    }

    // TODO update to new pattern with ExceptionHandler
    @PostMapping("/notifications")
    public ResponseEntity<List<Notification>> addNotificationsFromDbfFile(@RequestParam MultipartFile file, @CookieValue(name = "setaSessionId") String userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.addNotificationsFromDbfFile(file, userId));
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<Void> deleteAllNotifications(@CookieValue(name = "setaSessionId") String userId) {
        notificationService.deleteAllNotifications(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
