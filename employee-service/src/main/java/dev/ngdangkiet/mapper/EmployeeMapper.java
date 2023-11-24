package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.domain.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface EmployeeMapper extends ProtobufMapper<EmployeeEntity, PEmployee> {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Override
    @Mapping(source = "position.id", target = "positionId")
    @Mapping(target = "birthDay", qualifiedByName = "mapLocalDate2String")
    PEmployee toProtobuf(EmployeeEntity domain);

    @Override
    @Mapping(target = "birthDay", qualifiedByName = "mapString2LocalDate")
    EmployeeEntity toDomain(PEmployee protobuf);

    @Named("mapString2LocalDate")
    static LocalDate mapString2LocalDate(String birthDay) {
        return StringUtils.hasText(birthDay) ? LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    @Named("mapLocalDate2String")
    static String mapLocalDate2String(LocalDate birthDay) {
        return Objects.nonNull(birthDay) ? birthDay.toString() : EMPTY;
    }
}
