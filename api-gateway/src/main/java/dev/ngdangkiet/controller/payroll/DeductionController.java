package dev.ngdangkiet.controller.payroll;

import dev.ngdangkiet.client.PayrollGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PDeduction;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.payload.request.payroll.UpsertDeductionRequest;
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

@Tag(name = "Deduction", description = "Deduction APIs")
@RestController
@RequestMapping("/api/v1/deductions")
@RequiredArgsConstructor
public class DeductionController {

    private final PayrollGrpcClient payrollGrpcClient;

    @PostMapping
    public ApiMessage createDeduction(@Valid @RequestBody UpsertDeductionRequest upsertDeductionRequest) {
        try {
            var response = payrollGrpcClient.upsertDeduction(toBuilder(upsertDeductionRequest).build());
            return apiMessage((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateDeduction(@Valid @RequestBody UpsertDeductionRequest upsertDeductionRequest) {
        try {
            var response = payrollGrpcClient.upsertDeduction(toBuilder(upsertDeductionRequest)
                    .setId(upsertDeductionRequest.getId())
                    .build());
            return apiMessage((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    private PDeduction.Builder toBuilder(UpsertDeductionRequest upsertDeductionRequest) {
        return PDeduction.newBuilder()
                .setEmployeeId(upsertDeductionRequest.getEmployeeId())
                .setDeductionType(upsertDeductionRequest.getDeductionType())
                .setRate(upsertDeductionRequest.getRate())
                .setEffectiveDate(upsertDeductionRequest.getEffectiveDate());
    }

    private ApiMessage apiMessage(int code) {
        return ErrorHelper.isSuccess(code) ? ApiMessage.success(code) : ApiMessage.failed(code);
    }
}
