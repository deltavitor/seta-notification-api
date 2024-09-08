package edu.costavitor.setanotificationapi.notifications;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, String> {

    List<NotificationEntity> findAll();

    List<NotificationSummaryProjection> findAllByNumeroNotificationLocation(String numeroNotificationLocation);

    Optional<NotificationEntity> findByNuNotificacao(String nuNotificacao);
}
