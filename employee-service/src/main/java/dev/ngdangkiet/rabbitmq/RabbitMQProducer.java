package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.JsonMessage;
import dev.ngdangkiet.enums.EmailTemplate;
import dev.ngdangkiet.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendWelcomeNotification(Long receiverId) {
        JsonMessage message = JsonMessage.builder()
                .setSenderId(null)
                .setReceiverId(receiverId)
                .setNotificationType(NotificationType.WELCOME.name())
                .setMessage(String.format("Welcome new userId [%d]", receiverId))
                .build();
        log.info("Message sent -> userId [{}]", receiverId);
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.EXCHANGE, RabbitMQConstant.Notification.ROUTING_KEY, message);
    }

    public void sendChangePasswordNotification(Long receiverId) {
        JsonMessage message = JsonMessage.builder()
                .setSenderId(null)
                .setReceiverId(receiverId)
                .setNotificationType(NotificationType.CHANGE_PASSWORD.name())
                .setMessage(String.format("Change password successful for userId [%d]", receiverId))
                .build();
        log.info("Message sent -> userId [{}]", receiverId);
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.EXCHANGE, RabbitMQConstant.Notification.ROUTING_KEY, message);
    }

    // send email ...
    public void sendEmailActiveAccount(Long receiverId, String sendTo) {
        JsonMessage message = JsonMessage.builder()
                .setSenderId(null)
                .setReceiverId(receiverId)
                .setNotificationType(NotificationType.WELCOME.name())
                .setReceiverEmail(sendTo)
                .setEmailTemplate(EmailTemplate.INDEX.getValue())
                .setMessage(String.format("Welcome new userId [%d]", receiverId))
                .build();
        log.info(String.format("Message sent -> userId [%d]", receiverId));
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.EXCHANGE, RabbitMQConstant.Notification.ROUTING_KEY, message);
    }
}
