package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PUserTrackingActivity;
import dev.ngdangkiet.domain.UserTrackingDataActivity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface UserTrackingDataActivityMapper extends ProtobufMapper<UserTrackingDataActivity, PUserTrackingActivity> {

    UserTrackingDataActivityMapper INSTANCE = Mappers.getMapper(UserTrackingDataActivityMapper.class);
}
