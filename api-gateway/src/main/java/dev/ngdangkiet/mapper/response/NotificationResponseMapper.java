package dev.ngdangkiet.mapper.response;

import dev.ngdangkiet.dkmicroservices.notification.protobuf.PNotification;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface NotificationResponseMapper extends ProtobufMapper<NotificationResponse, PNotification> {

    NotificationResponseMapper INSTANCE = Mappers.getMapper(NotificationResponseMapper.class);
}
