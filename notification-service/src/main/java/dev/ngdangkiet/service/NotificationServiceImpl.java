package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsRequest;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsResponse;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PNotification;
import dev.ngdangkiet.DTO.EmailDTO;
import dev.ngdangkiet.domain.JsonMessage;
import dev.ngdangkiet.domain.NotificationEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.EmailMapper;
import dev.ngdangkiet.mapper.NotificationMapper;
import dev.ngdangkiet.mapper.RabbitMQNotificationMapper;
import dev.ngdangkiet.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RabbitMQNotificationMapper rabbitMQNotificationMapper = RabbitMQNotificationMapper.INSTANCE;
    private final NotificationMapper notificationMapper = NotificationMapper.INSTANCE;

    // RabbitMQ Services
    private final EmailMapper emailMapper = EmailMapper.INSTANCE;

    @Autowired
    EmailService emailService;


    @Override
    public void receiveNotification(JsonMessage message) {
        NotificationEntity entity = rabbitMQNotificationMapper.toDomain(message);
        notificationRepository.save(entity);
    }

    // Notification Services
    @Override
    public PGetNotificationsResponse getNotifications(PGetNotificationsRequest request) {
        PGetNotificationsResponse.Builder builder = PGetNotificationsResponse.newBuilder()
                .setCode(ErrorCode.SUCCESS);

        try {
            List<NotificationEntity> notifications = notificationRepository.getNotifications(
                    request.getReceiverId(),
                    request.getAll(),
                    request.getSeen()
            );
            List<PNotification> pNotifications = notificationMapper.toProtobufs(notifications);
            builder.addAllNotifications(pNotifications);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse seenOrUnseenNotification(Int64Value request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            NotificationEntity notification = notificationRepository.findById(request.getValue()).orElse(null);
            if (Objects.isNull(notification)) {
                log.error("Notification [{}] not found!", request.getValue());
                builder.setCode(ErrorCode.INVALID_DATA);
            } else {
                notification.setSeen(!notification.isSeen());
                notificationRepository.save(notification);
                builder.setCode(ErrorCode.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }


    @Override
    public void receiveEmailActiveAccount(JsonMessage message) {
        EmailDTO email = emailMapper.toDomain(message);
        emailService.sendMail(email, false);
    }
}
