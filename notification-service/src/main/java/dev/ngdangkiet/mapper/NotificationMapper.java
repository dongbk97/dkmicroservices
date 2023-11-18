package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.notification.protobuf.PNotification;
import dev.ngdangkiet.domain.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface NotificationMapper extends ProtobufMapper<NotificationEntity, PNotification> {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);
}
