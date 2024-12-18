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

@Service
@AllArgsConstructor
public class NotificationLocationService {

    private NotificationLocationRepository notificationLocationRepository;

    private NotificationLocationMapper notificationLocationMapper;

    private GeocodingApiWebClientService geocodingApiWebClientService;

    // Added the Lazy to prevent circular dependency, but as a TODO: this could be improved
    @Lazy
    private NotificationService notificationService;

    public List<NotificationLocation> findAllNotificationLocations() {

        return notificationLocationRepository
                .findAll()
                .stream()
                .map(notificationLocationMapper::mapToNotificationLocation)
                .map(notificationLocation -> enrichNotificationLocation(notificationLocation))
                .toList();
    }

    /**
     * Given an address that points to a Notification, provides a matching NotificationLocation. If a NotificationLocation with
     * the same latitude/longitude already exists in the database, no NotificationLocation will be created.
     * @param addresses
     * @return a new NotificationLocation, in case one wasn't present, or an already existing (matched) NotificationLocation
     */
    public NotificationLocation addNotificationLocationFromAddress(List<NotificationAddress> addresses) {

        Geocode geocode = geocodingApiWebClientService.getLocationByAddress(addresses);

        if (geocode == null) return null;
        return saveNotificationLocation(geocode);
    }

    public void deleteAllNotificationLocations() {
        notificationLocationRepository.deleteAll(notificationLocationRepository.findAll());
    }

    /**
     * Saves a NotificationLocation to the database using a Mutex (Java's synchronized). Since this method will be executed by multiple
     * threads in parallel, it uses a Mutex to prevent two NotificationLocations with the same latitude/longitude values from being saved
     * at once, allowing the future Notifications to re-use the single, already existing NotificationLocation.
     *
     * @param geocode
     * @return The saved NotificationLocationEntity
     */
    private synchronized NotificationLocation saveNotificationLocation(Geocode geocode) {
        Double latitude = geocode.getGeometry().getLocation().getLat();
        Double longitude = geocode.getGeometry().getLocation().getLng();

        return notificationLocationRepository.getNotificationLocationByLatitudeAndLongitude(latitude, longitude)
                .map(notificationLocationMapper::mapToNotificationLocation)
                .orElseGet(() -> {
                    NotificationLocationEntity entity = notificationLocationMapper.mapToNotificationLocationEntity(geocode);
                    return notificationLocationMapper.mapToNotificationLocation(notificationLocationRepository.save(entity));
                });
    }

    private NotificationLocation enrichNotificationLocation(NotificationLocation notificationLocation) {

        List<Notification> notifications = notificationService.findNotificationsByNumeroNotificationLocation(notificationLocation.getNumeroNotificationLocation());
        notificationLocation.setNotifications(notifications);
        return notificationLocation;
    }
}
