package dev.ngdangkiet.controller.payroll;

import dev.ngdangkiet.client.PayrollGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsRequest;
import dev.ngdangkiet.mapper.response.payroll.PayrollResponseMapper;
import dev.ngdangkiet.payload.request.payroll.GetPayrollsRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 12/17/2023
 */

@Tag(name = "Payroll", description = "Payroll APIs")
@RestController
@RequestMapping("/api/v1/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollGrpcClient payrollGrpcClient;
    private final PayrollResponseMapper payrollResponseMapper = PayrollResponseMapper.INSTANCE;

    @GetMapping
    public ApiMessage getPayrolls(@ModelAttribute GetPayrollsRequest request) {
        try {
            var response = payrollGrpcClient.getPayrolls(
                    PGetPayrollsRequest.newBuilder()
                            .setEmployeeId(ObjectUtils.defaultIfNull(request.getEmployeeId(), -1L))
                            .setMonth(ObjectUtils.defaultIfNull(request.getMonth(), -1))
                            .setYear(ObjectUtils.defaultIfNull(request.getYear(), -1))
                            .build()
            );
            return ApiMessage.success(payrollResponseMapper.toDomains(response.getDataList()));
        } catch (Exception e) {
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
