package edu.costavitor.setanotificationapi.notifications;

import com.linuxense.javadbf.DBFReader;
import edu.costavitor.setanotificationapi.common.BigDecimalToIntegerConverter;
import edu.costavitor.setanotificationapi.ibge.IbgeApiWebClientService;
import edu.costavitor.setanotificationapi.notification_locations.NotificationLocation;
import edu.costavitor.setanotificationapi.notification_locations.NotificationLocationService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Slf4j
public class NotificationCreationRunnableService implements Runnable {

    private List<Notification> notifications;

    private NotificationRepository notificationRepository;

    private NotificationMapper notificationMapper;

    private NotificationLocationService notificationLocationService;

    private IbgeApiWebClientService ibgeApiWebClientService;

    private MultipartFile file;

    private Integer startingIndex;

    private Integer chunkSize;

    private Boolean loggingEnabled;

    private String userId;

    @Override
    public void run() {
        // TODO properly handle exceptions
        try (InputStream inputStream = new BufferedInputStream(file.getInputStream()); DBFReader reader = new DBFReader(inputStream, true)) {
            Object[] row;
            int i = startingIndex;
            reader.skipRecords(startingIndex);

            while ((row = reader.nextRecord()) != null && i < startingIndex + chunkSize) {

                NotificationEntity notification = mapDbfRowToEntity(reader, row);
                notification.setUserId(userId);
                notification.setNotificationId(userId + ":" + notification.getNuNotificacao());
                NotificationLocation notificationLocation = notificationLocationService.addNotificationLocationFromAddress(getNotificationAddress(notification), userId);
                if (notificationLocation != null)
                    notification.setNumeroNotificationLocation(notificationLocation.getNumeroNotificationLocation());

                notificationRepository.save(notification);
                notifications.add(notificationMapper.mapToNotification(notification));

                // TODO improve logging
                if (loggingEnabled)
                    log.info("[T{}] [Chunk {}-{}] Completed row {}", Thread.currentThread().threadId(), startingIndex + 1, startingIndex + chunkSize + 1, i);
                i++;
            }

            if (loggingEnabled)
                log.info("[T{}] [Chunk {}-{}] Completed all rows", Thread.currentThread().threadId(), startingIndex + 1, startingIndex + chunkSize + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private List<NotificationAddress> getNotificationAddress(NotificationEntity notification) {

        String municipio = ibgeApiWebClientService.getMunicipioByCodigoMunicipio(notification.getCoMunicipioResidencia()).getNome();
        String uf = ibgeApiWebClientService.getUFByCodigoUF(notification.getCoUfNotificacao()).getSigla();
        String logradouro = notification.getNoLogradouroResidencia();

        String bairro = notification.getNoBairroResidencia();
        String numero = notification.getNuResidencia();
        // For now, we are not appending any descriptive labels to the components
        // because the API seems to work better without them

        return List.of(
                NotificationAddress.builder().logradouro(logradouro).numero(numero).municipio(municipio).uf(uf).build(),
                NotificationAddress.builder().logradouro(logradouro).numero(numero).bairro(bairro).municipio(municipio).uf(uf).build()
        );
    }
}
