package dev.ngdangkiet.mapper.response.employee;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.employee.EmployeeListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmployeeListResponseMapper extends ProtobufMapper<EmployeeListResponse, PEmployee> {

    EmployeeListResponseMapper INSTANCE = Mappers.getMapper(EmployeeListResponseMapper.class);

    @Override
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "position.id", target = "positionId")
    EmployeeListResponse toDomain(PEmployee protobuf);
}
