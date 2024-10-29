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
    public ResponseEntity<List<Notification>> findAllNotifications() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.findAllNotifications());
    }

    @GetMapping(value = "/notifications", params = {"returnUnmappedNotificationsOnly"})
    public ResponseEntity<List<Notification>> findAllUnmappedNotifications(@RequestParam("returnUnmappedNotificationsOnly") String returnUnmappedNotificationsOnly) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(notificationService.findAllUnmappedNotifications());
    }

    @GetMapping("/notifications/{numeroNotificacao}")
    public Notification getNotificationByNumeroNotificacao(@PathVariable("numeroNotificacao") String numeroNotificacao) {
        return notificationService.getNotificationByNumeroNotificacao(numeroNotificacao);
    }

    // TODO update to new pattern with ExceptionHandler
    @PostMapping("/notifications")
    public ResponseEntity<List<Notification>> addNotificationsFromDbfFile(@RequestParam MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.addNotificationsFromDbfFile(file));
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<Void> deleteAllNotifications() {
        notificationService.deleteAllNotifications();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
