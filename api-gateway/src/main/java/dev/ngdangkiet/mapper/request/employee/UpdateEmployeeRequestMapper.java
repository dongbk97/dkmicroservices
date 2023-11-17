package dev.ngdangkiet.mapper.request.employee;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.employee.UpdateEmployeeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface UpdateEmployeeRequestMapper extends ProtobufMapper<UpdateEmployeeRequest, PEmployee> {

    UpdateEmployeeRequestMapper INSTANCE = Mappers.getMapper(UpdateEmployeeRequestMapper.class);
}
