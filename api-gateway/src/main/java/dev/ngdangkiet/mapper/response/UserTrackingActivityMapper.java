package dev.ngdangkiet.mapper.response;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PUserTrackingActivity;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface UserTrackingActivityMapper extends ProtobufMapper<UserActivityData, PUserTrackingActivity> {

    UserTrackingActivityMapper INSTANCE = Mappers.getMapper(UserTrackingActivityMapper.class);
}
