package dev.ngdangkiet.mapper.request;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.auth.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface LoginRequestMapper extends ProtobufMapper<LoginRequest, PLoginRequest> {

    LoginRequestMapper INSTANCE = Mappers.getMapper(LoginRequestMapper.class);
}
