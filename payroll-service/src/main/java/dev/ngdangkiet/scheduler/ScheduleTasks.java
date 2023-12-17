package dev.ngdangkiet.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ngdangkiet.client.AttendanceGrpcClient;
import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.domain.DeductionEntity;
import dev.ngdangkiet.domain.PayrollEntity;
import dev.ngdangkiet.domain.SalaryEntity;
import dev.ngdangkiet.domain.TaxEntity;
import dev.ngdangkiet.domain.notification.alert.PayrollJsonMessage;
import dev.ngdangkiet.enums.notification.NotificationType;
import dev.ngdangkiet.rabbitmq.RabbitMQProducer;
import dev.ngdangkiet.repository.DeductionRepository;
import dev.ngdangkiet.repository.PayrollRepository;
import dev.ngdangkiet.repository.SalaryRepository;
import dev.ngdangkiet.repository.TaxRepository;
import dev.ngdangkiet.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleTasks {

    private static final int THRESHOLD = 50;
    private final EmployeeGrpcClient employeeGrpcClient;
    private final AttendanceGrpcClient attendanceGrpcClient;
    private final SalaryRepository salaryRepository;
    private final DeductionRepository deductionRepository;
    private final TaxRepository taxRepository;
    private final PayrollRepository payrollRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Scheduled(cron = "0 0 16 28,29,30,31 * ?")
    public void executeSendPayroll() {
        YearMonth yearMonth = YearMonth.now();
        if (LocalDate.now().getDayOfMonth() == yearMonth.atEndOfMonth().getDayOfMonth()) {
            ObjectMapper objectMapper = new ObjectMapper();

            // TODO: get salary, deduction, tax of all employee in system
            List<PayrollEntity> payrolls = new ArrayList<>();

            List<Long> employeeIds = employeeGrpcClient.getEmployees(PGetEmployeesRequest.newBuilder()
                            .setPositionId(-1L)
                            .setDepartmentId(-1L)
                            .build()).getDataList().stream()
                    .map(PEmployee::getId)
                    .toList();

            List<SalaryEntity> latestSalariesOfEachEmployee = salaryRepository.findLatestSalariesByEmployeeIds(employeeIds);
            List<DeductionEntity> latestDeductionsOfEachEmployee = deductionRepository.findLatestDeductionsByEmployeeIds(employeeIds);
            List<TaxEntity> latestTaxesOfEachEmployee = taxRepository.findLatestTaxesByEmployeeIds(employeeIds);

            List<PayrollJsonMessage.PayrollOfEachEmployee> payrollOfEachEmployees = new ArrayList<>();

            // TODO: calculate payroll for each employee
            employeeIds.forEach(eId -> {
                PGetTotalWorkingDayInMonthResponse.Data getTotalWorkingDayInMonthOfEmployee = attendanceGrpcClient.getTotalWorkingDayInMonthResponse(
                        PGetTotalWorkingDayInMonthRequest.newBuilder()
                                .setEmployeeId(eId)
                                .setMonth(yearMonth.getMonthValue())
                                .setYear(yearMonth.getYear())
                                .build()).getData();

                BigDecimal grossPay = latestSalariesOfEachEmployee.stream()
                        .filter(s -> s.getEmployeeId().equals(eId))
                        .map(SalaryEntity::getBaseSalary)
                        .findFirst()
                        .orElse(BigDecimal.ZERO);

                LocalDateTime payDate = LocalDateTime.now();

                PayrollJsonMessage.PayrollOfEachEmployee.newBuilder payrollOfEachEmployee = PayrollJsonMessage.PayrollOfEachEmployee.builder()
                        .setEmployeeId(eId)
                        .setMonth(yearMonth.getMonthValue())
                        .setYear(yearMonth.getYear())
                        .setPayDate(DateTimeUtil.formatLocalDateTimeNow(payDate))
                        .setGrossPay(grossPay);

                // TODO: calculate totalDeductions
                BigDecimal totalDeductions = BigDecimal.ZERO;
                List<PayrollJsonMessage.Deduction> deductions = new ArrayList<>();
                for (DeductionEntity entity : latestDeductionsOfEachEmployee.stream().filter(d -> d.getEmployeeId().equals(eId)).toList()) {
                    BigDecimal deduction = BigDecimal.valueOf(entity.getRate() / 100).multiply(grossPay);
                    totalDeductions = totalDeductions.add(deduction);
                    deductions.add(PayrollJsonMessage.Deduction.builder()
                            .setType(entity.getDeductionType().name())
                            .setRate(entity.getRate())
                            .setDeduction(deduction)
                            .build());
                }

                // TODO: calculate totalTaxes
                BigDecimal totalTaxes = BigDecimal.ZERO;
                List<PayrollJsonMessage.Tax> taxes = new ArrayList<>();
                for (TaxEntity entity : latestTaxesOfEachEmployee.stream().filter(t -> t.getEmployeeId().equals(eId)).toList()) {
                    BigDecimal tax = BigDecimal.valueOf(entity.getRate() / 100).multiply(grossPay);
                    totalTaxes = totalTaxes.add(tax);
                    taxes.add(PayrollJsonMessage.Tax.builder()
                            .setType(entity.getTaxType().name())
                            .setRate(entity.getRate())
                            .setTax(tax)
                            .build());
                }

                // TODO: set builder
                BigDecimal netPay = grossPay
                        .multiply(BigDecimal.valueOf(getTotalWorkingDayInMonthOfEmployee.getCurrentUserTotalDayWorking() / getTotalWorkingDayInMonthOfEmployee.getTotalDayOfMonth()))
                        .subtract(totalDeductions.add(totalTaxes));
                PayrollJsonMessage.PayrollOfEachEmployee.DeductionObj deductionObj = PayrollJsonMessage.PayrollOfEachEmployee.DeductionObj.builder()
                        .setDeductions(deductions)
                        .setTotalDeductions(totalDeductions)
                        .build();
                PayrollJsonMessage.PayrollOfEachEmployee.TaxObj taxObj = PayrollJsonMessage.PayrollOfEachEmployee.TaxObj.builder()
                        .setTaxes(taxes)
                        .setTotalTaxes(totalTaxes)
                        .build();

                payrollOfEachEmployee.setNetPay(netPay)
                        .setDeductionObj(deductionObj)
                        .setTaxObj(taxObj);

                payrollOfEachEmployees.add(payrollOfEachEmployee.build());
                try {
                    payrolls.add(PayrollEntity.builder()
                            .setEmployeeId(eId)
                            .setGrossPay(grossPay)
                            .setNetPay(netPay)
                            .setDeductionsJson(objectMapper.writeValueAsString(deductionObj))
                            .setTaxesJson(objectMapper.writeValueAsString(taxObj))
                            .setPayDate(payDate)
                            .build());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

            payrollRepository.saveAll(payrolls);

            // TODO: use rabbitMQ for send payrolls notification
            if (payrollOfEachEmployees.size() > THRESHOLD) {
                for (int i = 0; i < payrollOfEachEmployees.size(); i += THRESHOLD) {
                    sendPayroll(payrollOfEachEmployees.subList(i, Math.min(i + THRESHOLD, payrollOfEachEmployees.size())));
                }
            } else {
                sendPayroll(payrollOfEachEmployees);
            }
        }
    }

    private void sendPayroll(List<PayrollJsonMessage.PayrollOfEachEmployee> payrollOfEachEmployees) {
        rabbitMQProducer.sendPayrollNotification(PayrollJsonMessage.builder()
                .setNotificationType(NotificationType.ALERT.name())
                .setPayrollOfEachEmployees(payrollOfEachEmployees)
                .build());
    }
}
