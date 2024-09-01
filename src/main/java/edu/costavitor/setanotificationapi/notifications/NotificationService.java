package edu.costavitor.setanotificationapi.notifications;

import com.linuxense.javadbf.DBFReader;
import edu.costavitor.setanotificationapi.common.BigDecimalToIntegerConverter;
import edu.costavitor.setanotificationapi.ibge.IbgeApiWebClientService;
import edu.costavitor.setanotificationapi.notification_locations.NotificationLocation;
import edu.costavitor.setanotificationapi.notification_locations.NotificationLocationService;
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

    private NotificationLocationService notificationLocationService;

    private IbgeApiWebClientService ibgeApiWebClientService;

    public List<Notification> findAllNotifications() {

        return notificationRepository
                .findAll()
                .stream()
                .map(mapper::mapToNotification)
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
                NotificationLocation notificationLocation = notificationLocationService.addNotificationLocationFromAddress(getNotificationAddress(notification));
                notification.setNumeroNotificationLocation(notificationLocation.getNumeroNotificationLocation());

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

    private String getNotificationAddress(NotificationEntity notification) {

        String municipio = ibgeApiWebClientService.getMunicipioByCodigoMunicipio(notification.getCoMunicipioResidencia()).getNome();
        String uf = ibgeApiWebClientService.getUFByCodigoUF(notification.getCoUfNotificacao()).getSigla();
        String logradouro = notification.getNoLogradouroResidencia();
        // Gives more context to the logradouro in case it doesn't have a prefix
        if (!logradouro.toLowerCase().startsWith("rua") && !logradouro.toLowerCase().startsWith("avenida"))
            logradouro = "rua " + logradouro;
        String bairro = notification.getNoBairroResidencia();
        String numero = notification.getNuResidencia();
        return String.join(" ", logradouro, "numero", numero, "bairro", bairro, municipio, uf);
    }
}
