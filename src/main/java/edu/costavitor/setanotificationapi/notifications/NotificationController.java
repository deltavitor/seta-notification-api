package edu.costavitor.setanotificationapi.notifications;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/notifications")
    public ResponseEntity<List<Notification>> addNotificationsFromDbfFile(@RequestParam MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.addNotificationsFromDbfFile(file));
    }
}
