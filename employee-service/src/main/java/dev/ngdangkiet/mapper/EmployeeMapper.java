package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.domain.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmployeeMapper extends ProtobufMapper<EmployeeEntity, PEmployee> {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Override
    @Mapping(source = "position.id", target = "positionId")
    PEmployee toProtobuf(EmployeeEntity domain);
}
