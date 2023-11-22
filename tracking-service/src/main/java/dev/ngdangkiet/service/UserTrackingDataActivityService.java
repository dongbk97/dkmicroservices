package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.tracking.UserActivityData;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

public interface UserTrackingDataActivityService {

    void addTrackingUserActivity(UserActivityData userActivityData);
}
