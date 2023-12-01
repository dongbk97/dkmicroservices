package dev.ngdangkiet.mapper.response.employee;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.employee.EmployeeDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 12/1/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmployeeDetailResponseMapper extends ProtobufMapper<EmployeeDetailResponse, PEmployee> {

    EmployeeDetailResponseMapper INSTANCE = Mappers.getMapper(EmployeeDetailResponseMapper.class);
}
