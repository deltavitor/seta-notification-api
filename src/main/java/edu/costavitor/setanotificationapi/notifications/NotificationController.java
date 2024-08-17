package edu.costavitor.setanotificationapi.notifications;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Notification> findAllNotifications() {
        return notificationService.findAllNotifications();
    }
}
