package edu.costavitor.setanotificationapi.notification_locations;

import edu.costavitor.setanotificationapi.geocoding.Geocode;
import edu.costavitor.setanotificationapi.geocoding.GeocodingApiWebClientService;
import edu.costavitor.setanotificationapi.notifications.Notification;
import edu.costavitor.setanotificationapi.notifications.NotificationAddress;
import edu.costavitor.setanotificationapi.notifications.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationLocationService {

    private NotificationLocationRepository notificationLocationRepository;

    private NotificationLocationMapper notificationLocationMapper;

    private GeocodingApiWebClientService geocodingApiWebClientService;

    // Added the Lazy to prevent circular dependency, but as a TODO: this could be improved
    @Lazy
    private NotificationService notificationService;

    public List<NotificationLocation> findAllNotificationLocations(String userId) {

        return notificationLocationRepository
                .findAllByUserId(userId)
                .stream()
                .map(notificationLocationMapper::mapToNotificationLocation)
                .map(notificationLocation -> enrichNotificationLocation(notificationLocation, userId))
                .toList();
    }

    /**
     * Given an address that points to a Notification, provides a matching NotificationLocation. If a NotificationLocation with
     * the same latitude/longitude already exists in the database, no NotificationLocation will be created.
     * @param addresses
     * @return a new NotificationLocation, in case one wasn't present, or an already existing (matched) NotificationLocation
     */
    public NotificationLocation addNotificationLocationFromAddress(List<NotificationAddress> addresses, String userId) {

        Geocode geocode = geocodingApiWebClientService.getLocationByAddress(addresses);

        if (geocode == null) return null;
        return saveNotificationLocation(geocode, userId);
    }

    public void deleteAllNotificationLocations(String userId) {
        notificationLocationRepository.deleteAll(notificationLocationRepository.findAllByUserId(userId));
    }

    /**
     * Saves a NotificationLocation to the database using a Mutex (Java's synchronized). Since this method will be executed by multiple
     * threads in parallel, it uses a Mutex to prevent two NotificationLocations with the same latitude/longitude values from being saved
     * at once, allowing the future Notifications to re-use the single, already existing NotificationLocation.
     *
     * @param geocode
     * @return The saved NotificationLocationEntity
     */
    private synchronized NotificationLocation saveNotificationLocation(Geocode geocode, String userId) {
        Double latitude = geocode.getGeometry().getLocation().getLat();
        Double longitude = geocode.getGeometry().getLocation().getLng();

        return notificationLocationRepository.getNotificationLocationByLatitudeAndLongitudeAndUserId(latitude, longitude, userId)
                .map(notificationLocationMapper::mapToNotificationLocation)
                .orElseGet(() -> {
                    NotificationLocationEntity entity = notificationLocationMapper.mapToNotificationLocationEntity(geocode);
                    entity.setUserId(userId);
                    entity.setNumeroNotificationLocation(userId + ":" + UUID.randomUUID());
                    return notificationLocationMapper.mapToNotificationLocation(notificationLocationRepository.save(entity));
                });
    }

    private NotificationLocation enrichNotificationLocation(NotificationLocation notificationLocation, String userId) {

        List<Notification> notifications = notificationService.findNotificationsByNumeroNotificationLocation(notificationLocation.getNumeroNotificationLocation(), userId);
        notificationLocation.setNotifications(notifications);
        return notificationLocation;
    }
}
