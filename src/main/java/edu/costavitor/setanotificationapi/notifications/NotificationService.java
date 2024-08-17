package edu.costavitor.setanotificationapi.notifications;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;

    private NotificationMapper mapper;

    public List<Notification> findAllNotifications() {
        return notificationRepository
                .findAllProjectedBy()
                .stream()
                .map(mapper::mapToNotification)
                .toList();
    }
}
