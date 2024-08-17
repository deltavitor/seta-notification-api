package edu.costavitor.setanotificationapi.notifications;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface NotificationRepository extends Repository<NotificationEntity, String> {

    List<NotificationProjection> findAllProjectedBy();
}
