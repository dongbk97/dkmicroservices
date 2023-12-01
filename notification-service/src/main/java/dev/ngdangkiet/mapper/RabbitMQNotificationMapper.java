package dev.ngdangkiet.mapper;

import dev.ngdangkiet.domain.NotificationEntity;
import dev.ngdangkiet.domain.notification.alert.JsonMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface RabbitMQNotificationMapper extends ProtobufMapper<NotificationEntity, JsonMessage> {

    RabbitMQNotificationMapper INSTANCE = Mappers.getMapper(RabbitMQNotificationMapper.class);
}
