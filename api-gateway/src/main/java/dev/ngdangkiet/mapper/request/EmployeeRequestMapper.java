package dev.ngdangkiet.mapper.request;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.EmployeeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmployeeRequestMapper extends ProtobufMapper<EmployeeRequest, PEmployee> {

    EmployeeRequestMapper INSTANCE = Mappers.getMapper(EmployeeRequestMapper.class);
}
