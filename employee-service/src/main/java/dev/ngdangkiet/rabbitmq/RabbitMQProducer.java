package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.MessageConstant;
import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.constant.VariableConstant;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.domain.notification.JsonMessage;
import dev.ngdangkiet.domain.JsonMessageEmail;
import dev.ngdangkiet.enums.EmailTemplate;
import dev.ngdangkiet.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
                .setNotificationType(NotificationType.ALERT.name())
                .setMessage(String.format("Welcome new userId [%d]", receiverId))
                .build();
        log.info("Message sent -> userId [{}]", receiverId);
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.ALERT_EXCHANGE, RabbitMQConstant.Notification.ALERT_ROUTING_KEY, message);
    }

    public void sendChangePasswordNotification(Long receiverId) {
        JsonMessage message = JsonMessage.builder()
                .setSenderId(null)
                .setReceiverId(receiverId)
                .setNotificationType(NotificationType.ALERT.name())
                .setMessage(String.format("Change password successful for userId [%d]", receiverId))
                .build();
        log.info("Message sent -> userId [{}]", receiverId);
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.ALERT_EXCHANGE, RabbitMQConstant.Notification.ALERT_ROUTING_KEY, message);
    }

    // send email ...
    public void sendWelcomeEmail(PEmployee pEmployee) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(VariableConstant.Employee.FULLNAME, pEmployee.getFullName());
        JsonMessageEmail message = JsonMessageEmail.builder()
                .setReceiverId(pEmployee.getId())
                .setReceiverEmail(pEmployee.getEmail())
                .setEmailTemplate(EmailTemplate.WELCOME_NEW_USER.getValue())
                .setSubject(MessageConstant.Employee.WELCOME_NEW_USER)
                .setProperties(properties)
                .setIsHtml(Boolean.TRUE)
                .build();
        log.info(String.format("Message sent -> userId [{}]", pEmployee.getId()));
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.EMAIL_EXCHANGE, RabbitMQConstant.Notification.EMAIL_ROUTING_KEY, message);
    }
}
