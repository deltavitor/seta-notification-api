package edu.costavitor.setanotificationapi.notifications;

import com.linuxense.javadbf.DBFReader;
import edu.costavitor.setanotificationapi.common.BigDecimalToIntegerConverter;
import edu.costavitor.setanotificationapi.geocoding.GeocodingApiWebClientService;
import edu.costavitor.setanotificationapi.geocoding.Location;
import edu.costavitor.setanotificationapi.ibge.IbgeApiWebClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private NotificationRepository notificationRepository;

    private NotificationMapper mapper;

    private IbgeApiWebClientService ibgeApiWebClientService;

    private GeocodingApiWebClientService geocodingApiWebClientService;

    public List<Notification> findAllNotifications() {

        return notificationRepository
                .findAll()
                .stream()
                .map(mapper::mapToNotification)
                .toList();
    }

    public List<GeocodedNotification> findAllGeocodedNotifications() {

        return notificationRepository
                .findAllProjectedBy()
                .stream()
                .map(mapper::mapToGeocodedNotification)
                .toList();
    }

    // TODO properly handle exceptions
    // TODO improve efficiency (use threads, CompletableFuture)
    public List<Notification> addNotificationsFromDbfFile(String filePath) {

        List<Notification> notifications = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath); DBFReader reader = new DBFReader(fis)) {

            Object[] row;

            while ((row = reader.nextRecord()) != null) {

                NotificationEntity notification = mapDbfRowToEntity(reader, row);
                enrichNotification(notification);

                notificationRepository.save(notification);
                notifications.add(mapper.mapToNotification(notification));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }

    private NotificationEntity mapDbfRowToEntity(DBFReader reader, Object[] row) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        NotificationEntity.NotificationEntityBuilder builder = NotificationEntity.builder();

        for (int i = 0; i < reader.getFieldCount(); i++) {

            String dbfField = reader.getField(i).getName();
            String entityField = NotificationDBFMappings.NOTIFICATION_DBF_FIELD_TO_ENTITY_FIELD.get(dbfField);

            if (entityField != null && row[i] != null) {

                Object value = mapDbfValueToEntity(row[i]);

                Method setter = builder.getClass().getMethod(entityField, value.getClass());
                setter.invoke(builder, value);
            }
        }

        return builder.build();
    }

    private Object mapDbfValueToEntity(Object value) {

        if (value instanceof BigDecimal) return BigDecimalToIntegerConverter.bigDecimalToInteger((BigDecimal) value);
        return value;
    }

    private void enrichNotification(NotificationEntity notification) {

        notification.setNomeMunicipioResidencia(ibgeApiWebClientService.getMunicipioByCodigoMunicipio(notification.getCoMunicipioResidencia()).getNome());
        Location notificationLocation = geocodingApiWebClientService.getLocationByAddress(getNotificationAddress(notification));
        notification.setLatitude(notificationLocation.getLat());
        notification.setLongitude(notificationLocation.getLng());
    }

    private String getNotificationAddress(NotificationEntity notification) {

        String municipio = notification.getNomeMunicipioResidencia();
        String logradouro = notification.getNoLogradouroResidencia();
        // Gives more context to the logradouro in case it doesn't have a prefix
        if (!logradouro.toLowerCase().startsWith("rua") && logradouro.toLowerCase().startsWith("avenida"))
            logradouro = "rua " + logradouro;
        String bairro = notification.getNoBairroResidencia();
        String numero = notification.getNuResidencia();
        return String.join(" ", logradouro, "numero", numero, "bairro", bairro, municipio);
    }
}
