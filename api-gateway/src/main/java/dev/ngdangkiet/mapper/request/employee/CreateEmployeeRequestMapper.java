package dev.ngdangkiet.mapper.request.employee;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.employee.CreateEmployeeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface CreateEmployeeRequestMapper extends ProtobufMapper<CreateEmployeeRequest, PEmployee> {

    CreateEmployeeRequestMapper INSTANCE = Mappers.getMapper(CreateEmployeeRequestMapper.class);
}
