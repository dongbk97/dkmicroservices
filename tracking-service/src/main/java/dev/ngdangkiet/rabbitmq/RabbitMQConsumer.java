package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import dev.ngdangkiet.service.UserTrackingDataActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final UserTrackingDataActivityService trackingDataService;

    @RabbitListener(queues = {RabbitMQConstant.Tracking.USER_TRACKING_QUEUE})
    public void receiveTrackingUserActivity(UserActivityData userActivityData) {
        log.info("Received tracking user activity -> {}", userActivityData.toString());
        trackingDataService.addTrackingUserActivity(userActivityData);
    }
}
