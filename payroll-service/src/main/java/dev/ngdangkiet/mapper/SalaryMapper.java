package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PSalary;
import dev.ngdangkiet.domain.SalaryEntity;
import dev.ngdangkiet.enums.payroll.PayPeriod;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface SalaryMapper extends ProtobufMapper<SalaryEntity, PSalary> {

    SalaryMapper INSTANCE = Mappers.getMapper(SalaryMapper.class);

    @Named("mapBaseSalary2BigDecimal")
    static BigDecimal mapBaseSalary2BigDecimal(String baseSalary) {
        return new BigDecimal(baseSalary);
    }

    @Named("mapBaseSalary2String")
    static String mapBaseSalary2String(BigDecimal baseSalary) {
        return baseSalary.toString();
    }

    @Named("mapPayPeriod2Enum")
    static PayPeriod mapPayPeriod2Enum(String payPeriod) {
        return PayPeriod.of(payPeriod);
    }

    @Named("mapPayPeriod2String")
    static String mapPayPeriod2String(PayPeriod payPeriod) {
        return payPeriod.name();
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
    @Mapping(target = "baseSalary", qualifiedByName = "mapBaseSalary2BigDecimal")
    @Mapping(target = "payPeriod", qualifiedByName = "mapPayPeriod2Enum")
    @Mapping(target = "effectiveDate", qualifiedByName = "mapEffectiveDate2LocalDate")
    SalaryEntity toDomain(PSalary protobuf);

    @Override
    @Mapping(target = "baseSalary", qualifiedByName = "mapBaseSalary2String")
    @Mapping(target = "payPeriod", qualifiedByName = "mapPayPeriod2String")
    @Mapping(target = "effectiveDate", qualifiedByName = "mapEffectiveDate2String")
    PSalary toProtobuf(SalaryEntity domain);
}
