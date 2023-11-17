package dev.ngdangkiet.constant;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

public class RabbitMQConstant {

    public static class Notification {
        public static final String QUEUE = "notification_queue";
        public static final String EXCHANGE = "notification_exchange";
        public static final String ROUTING_KEY = "notification_routingKey";
    }
}
