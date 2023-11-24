package dev.ngdangkiet.mapper;

import dev.ngdangkiet.domain.JsonMessageEmail;
import dev.ngdangkiet.dto.EmailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author trongld
 * @since 11/16/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmailMapper extends ProtobufMapper<EmailDTO, JsonMessageEmail> {

    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);
}
