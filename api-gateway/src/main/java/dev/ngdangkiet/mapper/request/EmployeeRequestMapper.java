package dev.ngdangkiet.mapper.request;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.EmployeeRequest;
import org.mapstruct.Mapper;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmployeeRequestMapper extends ProtobufMapper<EmployeeRequest, PEmployee> {
}
