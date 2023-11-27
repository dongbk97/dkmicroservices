package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.location.protobuf.PGetLocationRequest;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface PLocationMapper extends ProtobufMapper<PLocationResponse, PGetLocationRequest> {
    PLocationMapper INSTANCE = Mappers.getMapper(PLocationMapper.class);

    @Override
    @Mapping(source = "allFields", target = "allFields", ignore = true)
    PLocationResponse toDomain(PGetLocationRequest protobuf);

    @Override
    @Mapping(source = "allFields", target = "allFields", ignore = true)
    PGetLocationRequest toProtobuf(PLocationResponse domain);
}