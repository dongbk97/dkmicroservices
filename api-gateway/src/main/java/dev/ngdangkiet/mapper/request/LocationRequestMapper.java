package dev.ngdangkiet.mapper.request;

import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocation;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.LocationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author TrongLD
 * @since 11/09/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface LocationRequestMapper extends ProtobufMapper<LocationRequest, PLocation> {

    LocationRequestMapper INSTANCE = Mappers.getMapper(LocationRequestMapper.class);
}
