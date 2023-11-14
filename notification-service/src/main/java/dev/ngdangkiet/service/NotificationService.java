package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.JsonMessage;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

public interface NotificationService {

    void receiveWelcomeNotification(JsonMessage message);
}
