package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PTax;
import dev.ngdangkiet.domain.TaxEntity;
import dev.ngdangkiet.enums.payroll.TaxType;
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
public interface TaxMapper extends ProtobufMapper<TaxEntity, PTax> {

    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    @Named("mapTaxType2Enum")
    static TaxType mapTaxType2Enum(String taxType) {
        return TaxType.of(taxType);
    }

    @Named("mapTaxType2String")
    static String mapTaxType2String(TaxType taxType) {
        return taxType.name();
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
    @Mapping(target = "taxType", qualifiedByName = "mapTaxType2Enum")
    @Mapping(target = "effectiveDate", qualifiedByName = "mapEffectiveDate2LocalDate")
    TaxEntity toDomain(PTax protobuf);

    @Override
    @Mapping(target = "taxType", qualifiedByName = "mapTaxType2String")
    @Mapping(target = "effectiveDate", qualifiedByName = "mapEffectiveDate2String")
    PTax toProtobuf(TaxEntity domain);
}
