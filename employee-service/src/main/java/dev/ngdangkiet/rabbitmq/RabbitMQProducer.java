package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.MessageConstant;
import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.constant.VariableConstant;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.domain.notification.alert.JsonMessage;
import dev.ngdangkiet.domain.notification.email.JsonMessageEmail;
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

    public void sendNewUserNotification(Long receiverId) {
        JsonMessage message = JsonMessage.builder()
                .setReceiverId(receiverId)
                .setNotificationType(NotificationType.ALERT.name())
                .setMessage(String.format("Welcome new userId [%d]", receiverId))
                .build();
        log.info("Message sent -> userId [{}]", receiverId);
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE, RabbitMQConstant.Notification.NEW_USER_NOTIFICATION_ROUTING_KEY, message);
    }

    public void sendChangePasswordNotification(Long receiverId) {
        JsonMessage message = JsonMessage.builder()
                .setReceiverId(receiverId)
                .setNotificationType(NotificationType.ALERT.name())
                .setMessage(String.format("Change password successful for userId [%d]", receiverId))
                .build();
        log.info("Message sent -> userId [{}]", receiverId);
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE, RabbitMQConstant.Notification.CHANGE_PASSWORD_NOTIFICATION_ROUTING_KEY, message);
    }

    // send email ...
    public void sendNewUserEmailNotification(PEmployee pEmployee) {
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
        log.info("Message sent -> userId [{}]", pEmployee.getId());
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NEW_USER_EMAIL_NOTIFICATION_QUEUE, RabbitMQConstant.Notification.NEW_USER_EMAIL_NOTIFICATION_ROUTING_KEY, message);
    }
}
