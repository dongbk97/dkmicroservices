package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PSearchHolidaysRequest;
import dev.ngdangkiet.domain.DTO.SearchHolidayDTO;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

@Mapper(config = ProtobufMapperConfig.class)
public interface SearchHolidayMapper extends ProtobufMapper<SearchHolidayDTO, PSearchHolidaysRequest> {

    SearchHolidayMapper INSTANCE = Mappers.getMapper(SearchHolidayMapper.class);

    @Named("mapDateFrom2LocalDate")
    static LocalDate mapDateFrom2LocalDate(String dateFrom) {
        return DateTimeUtil.convert2Localdate(dateFrom);
    }

    @Named("mapDateTo2LocalDate")
    static LocalDate mapDateTo2LocalDate(String dateTo) {
        return DateTimeUtil.convert2Localdate(dateTo);
    }

    @Named("mapDateFrom2String")
    static String mapDateFrom2String(LocalDate dateFrom) {
        return Objects.nonNull(dateFrom) ? dateFrom.toString() : EMPTY;
    }
    @Named("mapDateTo2String")
    static String mapDateTo2String(LocalDate dateTo) {
        return Objects.nonNull(dateTo) ? dateTo.toString() : EMPTY;
    }

    @Override
    @Mapping(target = "dateTo", qualifiedByName = "mapDateTo2LocalDate")
    @Mapping(target = "dateFrom", qualifiedByName = "mapDateFrom2LocalDate")
    SearchHolidayDTO toDomain(PSearchHolidaysRequest protobuf);

    @Override
    @Mapping(target = "dateTo", qualifiedByName = "mapDateTo2String")
    @Mapping(target = "dateFrom", qualifiedByName = "mapDateFrom2String")
    PSearchHolidaysRequest toProtobuf(SearchHolidayDTO domain);
}
