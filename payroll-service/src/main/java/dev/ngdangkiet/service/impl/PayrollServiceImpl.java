package dev.ngdangkiet.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PDeduction;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsRequest;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsResponse;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PPayroll;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PSalary;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PTax;
import dev.ngdangkiet.domain.DeductionEntity;
import dev.ngdangkiet.domain.PayrollEntity;
import dev.ngdangkiet.domain.SalaryEntity;
import dev.ngdangkiet.domain.TaxEntity;
import dev.ngdangkiet.domain.notification.alert.PayrollJsonMessage;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.DeductionMapper;
import dev.ngdangkiet.mapper.SalaryMapper;
import dev.ngdangkiet.mapper.TaxMapper;
import dev.ngdangkiet.repository.DeductionRepository;
import dev.ngdangkiet.repository.PayrollRepository;
import dev.ngdangkiet.repository.SalaryRepository;
import dev.ngdangkiet.repository.TaxRepository;
import dev.ngdangkiet.service.PayrollService;
import dev.ngdangkiet.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PayrollServiceImpl implements PayrollService {

    private final SalaryRepository salaryRepository;
    private final DeductionRepository deductionRepository;
    private final TaxRepository taxRepository;
    private final PayrollRepository payrollRepository;

    private final SalaryMapper salaryMapper = SalaryMapper.INSTANCE;
    private final DeductionMapper deductionMapper = DeductionMapper.INSTANCE;
    private final TaxMapper taxMapper = TaxMapper.INSTANCE;

    @Override
    public Int64Value upsertSalary(PSalary request) {
        long response = ErrorCode.FAILED;

        try {
            SalaryEntity salary;
            if (request.getId() > 0) {
                salary = salaryRepository.findById(request.getId()).orElse(null);
                if (Objects.isNull(salary)) {
                    log.error("Salary [{}] not found!", request.getId());
                    return Int64Value.of(ErrorCode.NOT_FOUND);
                }
            }

            salary = salaryMapper.toDomain(request);
            response = salaryRepository.save(salary).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    @Override
    public Int64Value upsertDeduction(PDeduction request) {
        long response = ErrorCode.FAILED;

        try {
            DeductionEntity deduction;
            if (request.getId() > 0) {
                deduction = deductionRepository.findById(request.getId()).orElse(null);
                if (Objects.isNull(deduction)) {
                    log.error("Deduction [{}] not found!", request.getId());
                    return Int64Value.of(ErrorCode.NOT_FOUND);
                }
            }

            deduction = deductionMapper.toDomain(request);
            response = deductionRepository.save(deduction).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    @Override
    public Int64Value upsertTax(PTax request) {
        long response = ErrorCode.FAILED;

        try {
            TaxEntity tax;
            if (request.getId() > 0) {
                tax = taxRepository.findById(request.getId()).orElse(null);
                if (Objects.isNull(tax)) {
                    log.error("Tax [{}] not found!", request.getId());
                    return Int64Value.of(ErrorCode.NOT_FOUND);
                }
            }

            tax = taxMapper.toDomain(request);
            response = taxRepository.save(tax).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    @Override
    public PGetPayrollsResponse getPayrolls(PGetPayrollsRequest request) {
        PGetPayrollsResponse.Builder builder = PGetPayrollsResponse.newBuilder().setCode(ErrorCode.SUCCESS);

        try {
            List<PayrollEntity> payrolls = payrollRepository.findPayrollByEmployeeIdAndYearMonth(
                    request.getEmployeeId(),
                    request.getMonth(),
                    request.getYear()
            );

            ObjectMapper objectMapper = new ObjectMapper();
            builder.addAllData(payrolls.stream()
                    .map(pr -> {
                        PPayroll.Builder pPayroll = PPayroll.newBuilder()
                                .setId(pr.getId())
                                .setEmployeeId(pr.getEmployeeId())
                                .setGrossPay(pr.getGrossPay().toString())
                                .setNetPay(pr.getNetPay().toString());

                        PayrollJsonMessage.PayrollOfEachEmployee.DeductionObj deductionObj;
                        try {
                            deductionObj = objectMapper.readValue(pr.getDeductionsJson(), PayrollJsonMessage.PayrollOfEachEmployee.DeductionObj.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        pPayroll.setDeductionObj(PPayroll.DeductionObj.newBuilder()
                                .addAllDeductions(deductionObj.getDeductions().stream()
                                        .map(d -> PPayroll.Deduction.newBuilder()
                                                .setType(d.getType())
                                                .setRate(d.getRate())
                                                .setDeduction(d.getDeduction().toString())
                                                .build())
                                        .collect(Collectors.toList()))
                                .setTotalDeductions(deductionObj.getTotalDeductions().toString())
                                .build());

                        PayrollJsonMessage.PayrollOfEachEmployee.TaxObj taxObj;
                        try {
                            taxObj = objectMapper.readValue(pr.getTaxesJson(), PayrollJsonMessage.PayrollOfEachEmployee.TaxObj.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        pPayroll.setTaxObj(PPayroll.TaxObj.newBuilder()
                                .addAllTaxes(taxObj.getTaxes().stream()
                                        .map(t -> PPayroll.Tax.newBuilder()
                                                .setType(t.getType())
                                                .setRate(t.getRate())
                                                .setTax(t.getTax().toString())
                                                .build())
                                        .collect(Collectors.toList()))
                                .setTotalTaxes(taxObj.getTotalTaxes().toString())
                                .build());

                        return pPayroll.setPayDate(DateTimeUtil.formatLocalDateTimeNow(pr.getPayDate())).build();
                    })
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
