package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void trackingUserActivity(UserActivityData userActivityData) {
        log.info("Tracking user activity [{}]", userActivityData.toString());
        rabbitTemplate.convertAndSend(RabbitMQConstant.Tracking.USER_TRACKING_EXCHANGE, RabbitMQConstant.Tracking.USER_TRACKING_ROUTING_KEY, userActivityData);
    }
}
