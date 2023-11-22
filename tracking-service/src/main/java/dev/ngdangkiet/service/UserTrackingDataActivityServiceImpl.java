package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.UserTrackingDataActivity;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import dev.ngdangkiet.mapper.UserTrackingDataActivityRabbitMQMapper;
import dev.ngdangkiet.repository.UserTrackingDataActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTrackingDataActivityServiceImpl implements UserTrackingDataActivityService {

    private final UserTrackingDataActivityRepository userTrackingDataActivityRepository;
    private final UserTrackingDataActivityRabbitMQMapper userTrackingDataActivityRabbitMQMapper = UserTrackingDataActivityRabbitMQMapper.INSTANCE;

    @Override
    public void addTrackingUserActivity(UserActivityData userActivityData) {
        UserTrackingDataActivity userTrackingDataActivity = userTrackingDataActivityRabbitMQMapper.toProtobuf(userActivityData);
        userTrackingDataActivityRepository.save(userTrackingDataActivity);
    }
}
