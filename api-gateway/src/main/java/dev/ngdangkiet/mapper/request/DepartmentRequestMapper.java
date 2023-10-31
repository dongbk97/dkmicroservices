package dev.ngdangkiet.mapper.request;

import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.DepartmentRequest;
import org.mapstruct.Mapper;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface DepartmentRequestMapper extends ProtobufMapper<DepartmentRequest, PDepartment> {
}
