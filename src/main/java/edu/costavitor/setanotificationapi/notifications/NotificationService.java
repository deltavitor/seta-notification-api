package edu.costavitor.setanotificationapi.notifications;

import com.linuxense.javadbf.DBFReader;
import edu.costavitor.setanotificationapi.common.BigDecimalToIntegerConverter;
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

    public List<Notification> findAllNotifications() {

        return notificationRepository
                .findAll()
                .stream()
                .map(mapper::mapToNotification)
                .toList();
    }

    // TODO properly handle exceptions
    public List<Notification> addNotificationsFromDbfFile(String filePath) {

        List<Notification> notifications = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath); DBFReader reader = new DBFReader(fis)) {

            Object[] row;

            while ((row = reader.nextRecord()) != null) {
                NotificationEntity notification = mapDbfRowToEntity(reader, row);
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
}
