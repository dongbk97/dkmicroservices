package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.JsonMessage;
import dev.ngdangkiet.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = {RabbitMQConstant.Notification.QUEUE})
    public void receiveWelcomeNotification(JsonMessage message) {
        log.info(String.format("Received message -> %s", message.toString()));
        notificationService.receiveWelcomeNotification(message);
    }
}
