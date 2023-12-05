package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.notification.alert.JsonMessage;
import dev.ngdangkiet.domain.notification.email.JsonMessageEmail;
import dev.ngdangkiet.service.EmailService;
import dev.ngdangkiet.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final NotificationService notificationService;

    private final EmailService emailService;

    @RabbitListener(queues = {RabbitMQConstant.Notification.NEW_USER_NOTIFICATION_QUEUE})
    public void receiveNewUserNotification(JsonMessage message) {
        log.info("Received message -> {}", message.toString());
        notificationService.receiveNewUserNotification(message);
    }

    @RabbitListener(queues = {RabbitMQConstant.Notification.NEW_APPLICANT_NOTIFICATION_QUEUE})
    public void receiveNewApplicantNotification(JsonMessage message) {
        log.info("Received message -> {}", message.toString());
        notificationService.receiveNewApplicantNotification(message);
    }

    @RabbitListener(queues = {RabbitMQConstant.Notification.NEW_LEAVE_REQUEST_NOTIFICATION_QUEUE})
    public void receiveNewLeaveRequestNotification(JsonMessage message) {
        log.info("Received message -> {}", message.toString());
        notificationService.receiveNewLeaveRequestNotification(message);
    }

    @RabbitListener(queues = {RabbitMQConstant.Notification.NEW_UPDATE_LEAVE_REQUEST_NOTIFICATION_QUEUE})
    public void receiveNewUpdateLeaveRequestNotification(JsonMessage message) {
        log.info("Received message -> {}", message.toString());
        notificationService.receiveNewUpdateLeaveRequestNotification(message);
    }

    @RabbitListener(queues = {RabbitMQConstant.Notification.NEW_USER_EMAIL_NOTIFICATION_QUEUE})
    public void receiveEmailNotification(JsonMessageEmail message) {
        log.info("Received message -> {}", message.toString());
        if (Objects.nonNull(message.getReceiverEmail())) {
            emailService.receiveEmailNotification(message);
        }
    }
}
