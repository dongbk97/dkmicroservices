package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.JsonMessage;
import dev.ngdangkiet.service.EmailService;
import dev.ngdangkiet.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = {RabbitMQConstant.Notification.QUEUE})
    public void receiveNotification(JsonMessage message) {
        log.info(String.format("Received message -> %s", message.toString()));
        if (Objects.nonNull(message.getEmailTemplate())) {
            notificationService.receiveEmailActiveAccount(message);
        } else {
            notificationService.receiveWelcomeNotification(message);
        }
    }
}
