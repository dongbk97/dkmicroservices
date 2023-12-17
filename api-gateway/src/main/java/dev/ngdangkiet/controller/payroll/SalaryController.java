package dev.ngdangkiet.controller.payroll;

import dev.ngdangkiet.client.PayrollGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PSalary;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.payload.request.payroll.UpsertSalaryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 12/17/2023
 */

@Tag(name = "Salary", description = "Salary APIs")
@RestController
@RequestMapping("/api/v1/salaries")
@RequiredArgsConstructor
public class SalaryController {

    private final PayrollGrpcClient payrollGrpcClient;

    @PostMapping
    public ApiMessage createSalary(@Valid @RequestBody UpsertSalaryRequest upsertSalaryRequest) {
        try {
            var response = payrollGrpcClient.upsertSalary(toBuilder(upsertSalaryRequest).build());
            return apiMessage((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateSalary(@Valid @RequestBody UpsertSalaryRequest upsertSalaryRequest) {
        try {
            var response = payrollGrpcClient.upsertSalary(toBuilder(upsertSalaryRequest)
                    .setId(upsertSalaryRequest.getId())
                    .build());
            return apiMessage((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    private PSalary.Builder toBuilder(UpsertSalaryRequest upsertSalaryRequest) {
        return PSalary.newBuilder()
                .setEmployeeId(upsertSalaryRequest.getEmployeeId())
                .setBaseSalary(upsertSalaryRequest.getBaseSalary())
                .setPayPeriod(upsertSalaryRequest.getPayPeriod())
                .setEffectiveDate(upsertSalaryRequest.getEffectiveDate());
    }

    private ApiMessage apiMessage(int code) {
        return ErrorHelper.isSuccess(code) ? ApiMessage.success(code) : ApiMessage.failed(code);
    }
}
