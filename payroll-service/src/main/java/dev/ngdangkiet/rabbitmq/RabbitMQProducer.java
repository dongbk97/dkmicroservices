package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.notification.alert.PayrollJsonMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendPayrollNotification(PayrollJsonMessage payrollJsonMessage) {
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE, RabbitMQConstant.Notification.PAYROLL_NOTIFICATION_ROUTING_KEY, payrollJsonMessage);
    }
}
