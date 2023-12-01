package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.ApplicantEntity;
import dev.ngdangkiet.domain.notification.alert.JsonMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendNewApplicantNotification(ApplicantEntity applicant) {
        JsonMessage message = JsonMessage.builder()
                .setMessage(String.format("New applicant [{%s}] apply for job [{%s}]", String.format("%s-%s", applicant.getId(), applicant.getEmail()), applicant.getJobPosting().toString()))
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE, RabbitMQConstant.Notification.NEW_APPLICANT_NOTIFICATION_ROUTING_KEY, message);
    }
}
