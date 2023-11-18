package dev.ngdangkiet.constant;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

public class RabbitMQConstant {

    public static class Notification {

        // ALERT
        public static final String ALERT_QUEUE = "notification_alert_queue";
        public static final String ALERT_EXCHANGE = "notification_alert_exchange";
        public static final String ALERT_ROUTING_KEY = "notification_alert_routingKey";

        // EMAIL
        public static final String EMAIL_QUEUE = "notification_email_queue";
        public static final String EMAIL_EXCHANGE = "notification_email_exchange";
        public static final String EMAIL_ROUTING_KEY = "notification_email_routingKey";

        // SMS
        public static final String SMS_QUEUE = "notification_sms_queue";
        public static final String SMS_EXCHANGE = "notification_sms_exchange";
        public static final String SMS_ROUTING_KEY = "notification_sms_routingKey";

        // BOT
        public static final String BOT_QUEUE = "notification_bot_queue";
        public static final String BOT_EXCHANGE = "notification_bot_exchange";
        public static final String BOT_ROUTING_KEY = "notification_bot_routingKey";
    }
}
