package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.JsonMessageEmail;
import dev.ngdangkiet.domain.notification.JsonMessage;
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

    @RabbitListener(queues = {RabbitMQConstant.Notification.ALERT_QUEUE})
    public void receiveAlertNotification(JsonMessage message) {
        log.info("Received message -> {}", message.toString());
        notificationService.receiveNotification(message);
    }

    @RabbitListener(queues = {RabbitMQConstant.Notification.EMAIL_QUEUE})
    public void receiveEmailNotification(JsonMessageEmail message) {
        log.info("Received message -> {}", message.toString());
        if (Objects.nonNull(message.getReceiverEmail())) {
            emailService.receiveEmailNotification(message);
        }
    }
}
