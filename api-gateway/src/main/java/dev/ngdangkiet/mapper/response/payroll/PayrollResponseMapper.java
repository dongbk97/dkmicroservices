package dev.ngdangkiet.mapper.response.payroll;

import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PPayroll;
import dev.ngdangkiet.domain.notification.alert.PayrollJsonMessage;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ngdangkiet
 * @since 12/17/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface PayrollResponseMapper extends ProtobufMapper<PayrollJsonMessage.PayrollOfEachEmployee, PPayroll> {

    PayrollResponseMapper INSTANCE = Mappers.getMapper(PayrollResponseMapper.class);

    @Named("mapPayDate2Month")
    static Integer mapPayDate2Month(String payDate) {
        return Objects.requireNonNull(DateTimeUtil.convert2LocalDateTime(payDate)).getMonthValue();
    }

    @Named("mapPayDate2Year")
    static Integer mapPayDate2Year(String payDate) {
        return Objects.requireNonNull(DateTimeUtil.convert2LocalDateTime(payDate)).getYear();
    }

    @Named("mapGrossPay2BigDecimal")
    static BigDecimal mapGrossPay2BigDecimal(String grossPay) {
        return new BigDecimal(grossPay);
    }

    @Named("mapNetPay2BigDecimal")
    static BigDecimal mapNetPay2BigDecimal(String netPay) {
        return new BigDecimal(netPay);
    }

    @Named("mapTaxObj")
    static PayrollJsonMessage.PayrollOfEachEmployee.TaxObj mapTaxObj(PPayroll.TaxObj taxObj) {
        return PayrollJsonMessage.PayrollOfEachEmployee.TaxObj.builder()
                .setTaxes(taxObj.getTaxesList().stream()
                        .map(t -> PayrollJsonMessage.Tax.builder()
                                .setType(t.getType())
                                .setRate(t.getRate())
                                .setTax(new BigDecimal(t.getTax()))
                                .build())
                        .collect(Collectors.toList()))
                .setTotalTaxes(new BigDecimal(taxObj.getTotalTaxes()))
                .build();
    }

    @Named("mapDeductionObj")
    static PayrollJsonMessage.PayrollOfEachEmployee.DeductionObj mapDeductionObj(PPayroll.DeductionObj deductionObj) {
        return PayrollJsonMessage.PayrollOfEachEmployee.DeductionObj.builder()
                .setDeductions(deductionObj.getDeductionsList().stream()
                        .map(t -> PayrollJsonMessage.Deduction.builder()
                                .setType(t.getType())
                                .setRate(t.getRate())
                                .setDeduction(new BigDecimal(t.getDeduction()))
                                .build())
                        .collect(Collectors.toList()))
                .setTotalDeductions(new BigDecimal(deductionObj.getTotalDeductions()))
                .build();
    }

    @Override
    @Mapping(target = "month", source = "payDate", qualifiedByName = "mapPayDate2Month")
    @Mapping(target = "year", source = "payDate", qualifiedByName = "mapPayDate2Year")
    @Mapping(target = "grossPay", qualifiedByName = "mapGrossPay2BigDecimal")
    @Mapping(target = "netPay", qualifiedByName = "mapNetPay2BigDecimal")
    @Mapping(target = "taxObj", qualifiedByName = "mapTaxObj")
    @Mapping(target = "deductionObj", qualifiedByName = "mapDeductionObj")
    PayrollJsonMessage.PayrollOfEachEmployee toDomain(PPayroll protobuf);
}
