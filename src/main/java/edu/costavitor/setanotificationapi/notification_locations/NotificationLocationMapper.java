package edu.costavitor.setanotificationapi.notification_locations;

import edu.costavitor.setanotificationapi.geocoding.Geocode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationLocationMapper {

    @Mapping(target = "numeroNotificationLocation", source = "numeroNotificationLocation")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "locationType", source = "locationType")
    @Mapping(target = "formattedAddress", source = "formattedAddress")
    NotificationLocation mapToNotificationLocation(NotificationLocationEntity source);

    @Mapping(target = "numeroNotificationLocation", ignore = true)
    @Mapping(target = "latitude", source = "geometry.location.lat")
    @Mapping(target = "longitude", source = "geometry.location.lng")
    @Mapping(target = "locationType", source = "geometry.locationType")
    @Mapping(target = "formattedAddress", source = "formattedAddress")
    NotificationLocationEntity mapToNotificationLocationEntity(Geocode source);
}
