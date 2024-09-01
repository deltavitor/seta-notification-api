package edu.costavitor.setanotificationapi.notifications;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, String> {

    List<NotificationEntity> findAll();
}
