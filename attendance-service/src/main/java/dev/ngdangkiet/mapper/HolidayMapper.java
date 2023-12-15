package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PHolidayRecord;
import dev.ngdangkiet.domain.HolidayEntity;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface HolidayMapper extends ProtobufMapper<HolidayEntity, PHolidayRecord> {

    HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    @Named("mapDate2LocalDate")
    static LocalDate mapDate2LocalDate(String date) {
        return DateTimeUtil.convert2Localdate(date);
    }

    @Named("mapDate2String")
    static String mapDate2String(LocalDate date) {
        return Objects.nonNull(date) ? date.toString() : EMPTY;
    }

    @Override
    @Mapping(target = "date", qualifiedByName = "mapDate2LocalDate")
    HolidayEntity toDomain(PHolidayRecord protobuf);

    @Override
    @Mapping(target = "date", qualifiedByName = "mapDate2String")
    PHolidayRecord toProtobuf(HolidayEntity domain);
}
