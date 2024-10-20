package edu.costavitor.setanotificationapi.notification_locations;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
@Getter
@Setter
@Builder
public class NotificationLocationEntity {

    @Id
    private String numeroNotificationLocation;

    @Indexed
    private String userId;

    @Indexed
    private Double latitude;

    @Indexed
    private Double longitude;

    private String locationType;

    private String formattedAddress;

    private Boolean partialMatch;
}
