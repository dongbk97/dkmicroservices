package dev.ngdangkiet.repository;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesRequest;
import dev.ngdangkiet.domain.UserTrackingDataActivity;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

public interface UserTrackingDataActivityRepositoryCustom {

    List<UserTrackingDataActivity> findActivities(PGetUserTrackingActivitiesRequest request);
}
