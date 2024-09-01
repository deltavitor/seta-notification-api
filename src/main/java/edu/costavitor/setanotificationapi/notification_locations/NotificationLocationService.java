package edu.costavitor.setanotificationapi.notification_locations;

import edu.costavitor.setanotificationapi.geocoding.Geocode;
import edu.costavitor.setanotificationapi.geocoding.GeocodingApiWebClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationLocationService {

    private NotificationLocationRepository notificationLocationRepository;

    private NotificationLocationMapper notificationLocationMapper;

    private GeocodingApiWebClientService geocodingApiWebClientService;

    public List<NotificationLocation> findAllNotificationLocations() {

        return notificationLocationRepository.findAll()
                .stream()
                .map(notificationLocationMapper::mapToNotificationLocation)
                .toList();
    }

    /**
     * Given an address that points to a Notification, provides a matching NotificationLocation. If a NotificationLocation with
     * the same latitude/longitude already exists in the database, no NotificationLocation will be created.
     * @param address
     * @return a new NotificationLocation, in case one wasn't present, or an already existing (matched) NotificationLocation
     */
    public NotificationLocation addNotificationLocationFromAddress(String address) {

        Geocode geocode = geocodingApiWebClientService.getLocationByAddress(address);
        Double latitude = geocode.getGeometry().getLocation().getLat();
        Double longitude = geocode.getGeometry().getLocation().getLng();

        return notificationLocationRepository.findByLatitudeAndLongitude(latitude, longitude)
                .map(notificationLocationMapper::mapToNotificationLocation)
                .orElseGet(() -> {
                    NotificationLocationEntity notificationLocationEntity = notificationLocationMapper.mapToNotificationLocationEntity(geocode);
                    return notificationLocationMapper.mapToNotificationLocation(saveNotificationLocation(notificationLocationEntity));
                });
    }

    /**
     * Saves a NotificationLocation to the database using a Mutex (Java's synchronized). Since this method will be executed by multiple
     * threads in parallel, it uses a Mutex to prevent two NotificationLocations with the same latitude/longitude values from being saved
     * at once, allowing the future Notifications to re-use the single, already existing NotificationLocation.
     *
     * @param notificationLocationEntity
     * @return The saved NotificationLocationEntity
     */
    private synchronized NotificationLocationEntity saveNotificationLocation(NotificationLocationEntity notificationLocationEntity) {
        return notificationLocationRepository.save(notificationLocationEntity);
    }
}
