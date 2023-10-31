package dev.ngdangkiet.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@MapperConfig(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public class ProtobufMapperConfig {
}
