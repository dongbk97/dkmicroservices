package dev.ngdangkiet.mapper.response;

import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocation;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.location.LocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author TrongLD
 * @since 11/09/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface LocationResponseMapper extends ProtobufMapper<LocationResponse, PLocation> {

    LocationResponseMapper INSTANCE = Mappers.getMapper(LocationResponseMapper.class);
}
