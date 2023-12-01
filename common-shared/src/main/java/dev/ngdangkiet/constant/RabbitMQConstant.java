package dev.ngdangkiet.constant;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

public class RabbitMQConstant {

    public static class Tracking {
        public static final String USER_TRACKING_EXCHANGE = "user_tracking_exchange";
        public static final String USER_TRACKING_QUEUE = "user_tracking_queue";
        public static final String USER_TRACKING_ROUTING_KEY = "user_tracking_routing_key";
    }

    public static class Notification {
        public static final String NOTIFICATION_EXCHANGE = "notification_exchange";

        public static final String NEW_USER_NOTIFICATION_QUEUE = "new_user_notification_queue";
        public static final String NEW_USER_EMAIL_NOTIFICATION_QUEUE = "new_user_email_notification_queue";
        public static final String NEW_USER_NOTIFICATION_ROUTING_KEY = "new_user_notification_routing_key";
        public static final String NEW_USER_EMAIL_NOTIFICATION_ROUTING_KEY = "new_user_email_notification_routing_key";

        public static final String CHANGE_PASSWORD_NOTIFICATION_QUEUE = "change_password_notification_queue";
        public static final String CHANGE_PASSWORD_NOTIFICATION_ROUTING_KEY = "change_password_notification_routing_key";

        public static final String NEW_APPLICANT_NOTIFICATION_QUEUE = "new_applicant_notification_queue";
        public static final String NEW_APPLICANT_NOTIFICATION_ROUTING_KEY = "new_applicant_notification_routing_key";
    }
}
