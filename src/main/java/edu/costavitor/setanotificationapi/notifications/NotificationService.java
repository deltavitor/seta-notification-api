package edu.costavitor.setanotificationapi.notifications;

import com.linuxense.javadbf.DBFReader;
import edu.costavitor.setanotificationapi.common.DBFUtils;
import edu.costavitor.setanotificationapi.ibge.IbgeApiWebClientService;
import edu.costavitor.setanotificationapi.notification_locations.NotificationLocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;

    private NotificationMapper notificationMapper;

    private NotificationLocationService notificationLocationService;

    private IbgeApiWebClientService ibgeApiWebClientService;

    @Value("${seta.notification.runnable.chunk-size:10}")
    private Integer chunkSize;

    @Value("${seta.logging.runnable:false}")
    private Boolean runnableLoggingEnabled;

    public List<Notification> findAllNotifications() {

        return notificationRepository
                .findAll()
                .stream()
                .map(notificationMapper::mapToNotification)
                .toList();
    }

    // TODO properly handle exceptions
    public List<Notification> addNotificationsFromDbfFile(String filePath) {

        List<Notification> notifications = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             DBFReader reader = new DBFReader(fis);
             var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            Integer numberOfRecords = DBFUtils.getNumberOfRecords(reader);
            int numberOfThreads = (int) Math.ceil((double) numberOfRecords / chunkSize);

            CompletableFuture<Void> futures = CompletableFuture.allOf(
                    IntStream.range(0, numberOfThreads)
                            .mapToObj(i -> CompletableFuture.runAsync(
                                    NotificationCreationRunnableService.builder()
                                            .notifications(notifications)
                                            .notificationRepository(notificationRepository)
                                            .notificationMapper(notificationMapper)
                                            .notificationLocationService(notificationLocationService)
                                            .ibgeApiWebClientService(ibgeApiWebClientService)
                                            .filePath(filePath)
                                            .startingIndex(i * chunkSize)
                                            .chunkSize(chunkSize)
                                            .loggingEnabled(runnableLoggingEnabled)
                                            .build(),
                                    executor
                            ))
                            .toArray(CompletableFuture[]::new)
            );

            futures.get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }
}
