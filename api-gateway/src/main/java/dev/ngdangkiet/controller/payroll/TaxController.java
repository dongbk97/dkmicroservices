package dev.ngdangkiet.controller.payroll;

import dev.ngdangkiet.client.PayrollGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PTax;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.payload.request.payroll.UpsertTaxRequest;
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

@Tag(name = "Tax", description = "Tax APIs")
@RestController
@RequestMapping("/api/v1/taxes")
@RequiredArgsConstructor
public class TaxController {

    private final PayrollGrpcClient payrollGrpcClient;

    @PostMapping
    public ApiMessage createTax(@Valid @RequestBody UpsertTaxRequest upsertTaxRequest) {
        try {
            var response = payrollGrpcClient.upsertTax(toBuilder(upsertTaxRequest).build());
            return apiMessage((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateTax(@Valid @RequestBody UpsertTaxRequest upsertTaxRequest) {
        try {
            var response = payrollGrpcClient.upsertTax(toBuilder(upsertTaxRequest)
                    .setId(upsertTaxRequest.getId())
                    .build());
            return apiMessage((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    private PTax.Builder toBuilder(UpsertTaxRequest upsertTaxRequest) {
        return PTax.newBuilder()
                .setEmployeeId(upsertTaxRequest.getEmployeeId())
                .setTaxType(upsertTaxRequest.getTaxType())
                .setRate(upsertTaxRequest.getRate())
                .setEffectiveDate(upsertTaxRequest.getEffectiveDate());
    }

    private ApiMessage apiMessage(int code) {
        return ErrorHelper.isSuccess(code) ? ApiMessage.success(code) : ApiMessage.failed(code);
    }
}