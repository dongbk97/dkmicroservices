package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.JsonMessage;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

public interface NotificationService {

    void receiveNotification(JsonMessage message);

    PGetNotificationsResponse getNotifications(PGetNotificationsRequest request);

    EmptyResponse seenOrUnseenNotification(Int64Value request);

    void receiveEmailActiveAccount(JsonMessage message);
}
