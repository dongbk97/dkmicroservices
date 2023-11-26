package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.domain.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/26/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface PositionMapper extends ProtobufMapper<PositionEntity, PPosition> {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    @Override
    @Mapping(target = "name", qualifiedByName = "mapProtobufToDomain")
    PositionEntity toDomain(PPosition protobuf);

    @Named("mapProtobufToDomain")
    static String mapProtobufToDomain(String name) {
        return name.toUpperCase();
    }
}
