package dev.ngdangkiet.mapper.response;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface LoginResponseMapper extends ProtobufMapper<LoginResponse, PLoginResponse> {

    LoginResponseMapper INSTANCE = Mappers.getMapper(LoginResponseMapper.class);
}
