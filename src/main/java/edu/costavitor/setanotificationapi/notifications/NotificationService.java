package edu.costavitor.setanotificationapi.notifications;

import com.linuxense.javadbf.DBFReader;
import edu.costavitor.setanotificationapi.common.DBFUtils;
import edu.costavitor.setanotificationapi.common.exceptions.EntityNotFoundException;
import edu.costavitor.setanotificationapi.ibge.IbgeApiWebClientService;
import edu.costavitor.setanotificationapi.notification_locations.NotificationLocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
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

    public List<Notification> findAllNotifications(String userId) {

        return notificationRepository
                .findAllByUserId(userId)
                .stream()
                .map(notificationMapper::mapToNotification)
                .toList();
    }

    public List<Notification> findAllUnmappedNotifications(String userId) {

        return notificationRepository
                .findAllByUserId(userId)
                .stream()
                .filter(notification -> notification.getNumeroNotificationLocation() == null)
                .map(notificationMapper::mapToNotification)
                .toList();
    }

    public List<Notification> findNotificationsByNumeroNotificationLocation(String numeroNotificationLocation, String userId) {

        return notificationRepository
                .findNotificationsByNumeroNotificationLocationAndUserId(numeroNotificationLocation, userId)
                .stream()
                .map(notificationMapper::mapToNotification)
                .toList();
    }

    public Notification getNotificationByNumeroNotificacao(String numeroNotificacao, String userId) {

        return notificationRepository
                .getNotificationByNuNotificacaoAndUserId(numeroNotificacao, userId)
                .map(notificationMapper::mapToNotification)
                .orElseThrow(() -> new EntityNotFoundException("Notification with numeroNotificacao " + numeroNotificacao + " not found"));
    }

    // TODO properly handle exceptions
    public List<Notification> addNotificationsFromDbfFile(MultipartFile file, String userId) {

        List<Notification> notifications = new ArrayList<>();

        try (InputStream inputStream = new BufferedInputStream(file.getInputStream());
             DBFReader reader = new DBFReader(inputStream);
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
                                            .file(file)
                                            .startingIndex(i * chunkSize)
                                            .chunkSize(chunkSize)
                                            .loggingEnabled(runnableLoggingEnabled)
                                            .userId(userId)
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

    public void deleteAllNotifications(String userId) {

        notificationRepository.deleteAll(notificationRepository.findAllByUserId(userId));
    }
}
