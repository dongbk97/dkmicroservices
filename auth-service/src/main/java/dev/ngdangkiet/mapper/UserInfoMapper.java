package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/8/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface UserInfoMapper extends ProtobufMapper<EmployeeEntity, PLoginResponse.UserInfo> {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);
}
