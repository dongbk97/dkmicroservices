package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PDeduction;
import dev.ngdangkiet.domain.DeductionEntity;
import dev.ngdangkiet.enums.payroll.DeductionType;
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
 * @since 12/16/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface DeductionMapper extends ProtobufMapper<DeductionEntity, PDeduction> {

    DeductionMapper INSTANCE = Mappers.getMapper(DeductionMapper.class);

    @Named("mapDeductionType2Enum")
    static DeductionType mapDeductionType2Enum(String deductionType) {
        return DeductionType.of(deductionType);
    }

    @Named("mapDeductionType2String")
    static String mapDeductionType2String(DeductionType deductionType) {
        return deductionType.name();
    }

    @Named("mapEffectiveDate2LocalDate")
    static LocalDate mapEffectiveDate2LocalDate(String effectiveDate) {
        return DateTimeUtil.convert2Localdate(effectiveDate);
    }

    @Named("mapEffectiveDate2String")
    static String mapEffectiveDate2String(LocalDate effectiveDate) {
        return Objects.nonNull(effectiveDate) ? effectiveDate.toString() : EMPTY;
    }

    @Override
    @Mapping(target = "deductionType", qualifiedByName = "mapDeductionType2Enum")
    @Mapping(target = "effectiveDate", qualifiedByName = "mapEffectiveDate2LocalDate")
    DeductionEntity toDomain(PDeduction protobuf);

    @Override
    @Mapping(target = "deductionType", qualifiedByName = "mapDeductionType2String")
    @Mapping(target = "effectiveDate", qualifiedByName = "mapEffectiveDate2String")
    PDeduction toProtobuf(DeductionEntity domain);
}
