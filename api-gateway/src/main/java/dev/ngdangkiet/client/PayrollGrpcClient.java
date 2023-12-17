package dev.ngdangkiet.client;

import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PDeduction;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsRequest;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsResponse;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PSalary;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PTax;
import dev.ngdangkiet.dkmicroservices.payroll.service.PayrollServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/17/2023
 */

@Component
@RequiredArgsConstructor
public class PayrollGrpcClient {

    @GrpcClient("grpc-payroll-service")
    private final PayrollServiceGrpc.PayrollServiceBlockingStub payrollServiceBlockingStub;

    // Salary
    public long upsertSalary(PSalary request) {
        return payrollServiceBlockingStub.upsertSalary(request).getValue();
    }

    // Deduction
    public long upsertDeduction(PDeduction request) {
        return payrollServiceBlockingStub.upsertDeduction(request).getValue();
    }

    // Tax
    public long upsertTax(PTax request) {
        return payrollServiceBlockingStub.upsertTax(request).getValue();
    }

    // Payroll
    public PGetPayrollsResponse getPayrolls(PGetPayrollsRequest request) {
        return payrollServiceBlockingStub.getPayrolls(request);
    }
}
