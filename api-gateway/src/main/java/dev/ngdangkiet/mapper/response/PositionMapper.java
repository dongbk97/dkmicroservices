package dev.ngdangkiet.mapper.response;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.employee.PositionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/26/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface PositionMapper extends ProtobufMapper<PositionResponse, PPosition> {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);
}
