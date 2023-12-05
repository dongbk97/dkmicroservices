package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.LeaveRequestEntity;
import dev.ngdangkiet.domain.notification.alert.JsonMessage;
import dev.ngdangkiet.enums.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendNewLeaveRequestNotification(LeaveRequestEntity leaveRequest) {
        JsonMessage message = JsonMessage.builder()
                .setSenderId(leaveRequest.getEmployeeId())
                .setReceiverId(leaveRequest.getReceiverId())
                .setNotificationType(NotificationType.ALERT.name())
                .setMessage(String.format("New leave request [{%s}] send to you [{%d}]. Please confirm (APPROVE OR REJECT) submit!", leaveRequest.toString(), leaveRequest.getReceiverId()))
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE, RabbitMQConstant.Notification.NEW_LEAVE_REQUEST_NOTIFICATION_ROUTING_KEY, message);
    }

    public void sendNewUpdateLeaveRequestNotification(LeaveRequestEntity leaveRequest) {
        JsonMessage message = JsonMessage.builder()
                .setReceiverId(leaveRequest.getEmployeeId())
                .setNotificationType(NotificationType.ALERT.name())
                .setMessage(String.format("New update leave request [{%s}] send to you [{%d}]", leaveRequest.toString(), leaveRequest.getEmployeeId()))
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE, RabbitMQConstant.Notification.NEW_UPDATE_LEAVE_REQUEST_NOTIFICATION_ROUTING_KEY, message);
    }
}
