package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PDeduction;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsRequest;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsResponse;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PSalary;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PTax;
import dev.ngdangkiet.dkmicroservices.payroll.service.PayrollServiceGrpc;
import dev.ngdangkiet.service.PayrollService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@GrpcService
@RequiredArgsConstructor
public class PayrollServiceGrpcServer extends PayrollServiceGrpc.PayrollServiceImplBase {

    private final PayrollService payrollService;

    @Override
    public void upsertSalary(PSalary request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = payrollService.upsertSalary(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void upsertDeduction(PDeduction request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = payrollService.upsertDeduction(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void upsertTax(PTax request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = payrollService.upsertTax(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPayrolls(PGetPayrollsRequest request, StreamObserver<PGetPayrollsResponse> responseObserver) {
        PGetPayrollsResponse response = payrollService.getPayrolls(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
