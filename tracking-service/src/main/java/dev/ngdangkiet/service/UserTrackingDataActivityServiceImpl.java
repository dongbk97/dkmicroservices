package dev.ngdangkiet.service;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesRequest;
import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesResponse;
import dev.ngdangkiet.domain.UserTrackingDataActivity;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.UserTrackingDataActivityMapper;
import dev.ngdangkiet.mapper.UserTrackingDataActivityRabbitMQMapper;
import dev.ngdangkiet.repository.UserTrackingDataActivityRepository;
import dev.ngdangkiet.repository.UserTrackingDataActivityRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTrackingDataActivityServiceImpl implements UserTrackingDataActivityService {

    private final UserTrackingDataActivityRepository userTrackingDataActivityRepository;
    private final UserTrackingDataActivityRepositoryCustom userTrackingDataActivityRepositoryCustom;
    private final UserTrackingDataActivityRabbitMQMapper userTrackingDataActivityRabbitMQMapper = UserTrackingDataActivityRabbitMQMapper.INSTANCE;

    private final UserTrackingDataActivityMapper userTrackingDataActivityMapper = UserTrackingDataActivityMapper.INSTANCE;

    @Override
    public void addTrackingUserActivity(UserActivityData userActivityData) {
        UserTrackingDataActivity userTrackingDataActivity = userTrackingDataActivityRabbitMQMapper.toProtobuf(userActivityData);
        userTrackingDataActivityRepository.save(userTrackingDataActivity);
    }

    @Override
    public PGetUserTrackingActivitiesResponse getUserTrackingActivities(PGetUserTrackingActivitiesRequest request) {
        PGetUserTrackingActivitiesResponse.Builder builder = PGetUserTrackingActivitiesResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            List<UserTrackingDataActivity> userTrackingDataActivities = userTrackingDataActivityRepositoryCustom.findActivities(request);
            builder.setCode(ErrorCode.SUCCESS).addAllData(CollectionUtils.isEmpty(userTrackingDataActivities)
                    ? Collections.emptyList() : userTrackingDataActivityMapper.toProtobufs(userTrackingDataActivities));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error when get user tracking activities [{}]", e.getMessage());
        }
        return builder.build();
    }
}
