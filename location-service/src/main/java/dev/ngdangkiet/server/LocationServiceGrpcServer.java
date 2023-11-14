package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.service.EmployeeService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@GrpcService
@RequiredArgsConstructor
public class EmployeeServiceGrpcServer extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    private final EmployeeService employeeService;

    @Override
    public void createOrUpdateEmployee(PEmployee request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = employeeService.createOrUpdateEmployee(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployeeById(Int64Value request, StreamObserver<PEmployeeResponse> responseObserver) {
        PEmployeeResponse response = employeeService.getEmployeeById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployees(PGetEmployeesRequest request, StreamObserver<PEmployeesResponse> responseObserver) {
        PEmployeesResponse response = employeeService.getEmployees(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmployeeById(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = employeeService.deleteEmployeeById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
