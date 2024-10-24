package edu.costavitor.setanotificationapi.notification_locations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationLocationRepository extends CrudRepository<NotificationLocationEntity, String> {

    List<NotificationLocationEntity> findAllByUserId(String userId);

    Optional<NotificationLocationEntity> getNotificationLocationByLatitudeAndLongitudeAndUserId(Double latitude, Double longitude, String userId);
}
