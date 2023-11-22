package dev.ngdangkiet.mapper;

import dev.ngdangkiet.domain.UserTrackingDataActivity;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface UserTrackingDataActivityRabbitMQMapper extends ProtobufMapper<UserActivityData, UserTrackingDataActivity> {

    UserTrackingDataActivityRabbitMQMapper INSTANCE = Mappers.getMapper(UserTrackingDataActivityRabbitMQMapper.class);
}
