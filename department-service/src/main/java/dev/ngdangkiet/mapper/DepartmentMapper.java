package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.domain.DepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface DepartmentMapper extends ProtobufMapper<DepartmentEntity, PDepartment> {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
}
