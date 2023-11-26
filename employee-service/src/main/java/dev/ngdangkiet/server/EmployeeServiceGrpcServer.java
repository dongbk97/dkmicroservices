package dev.ngdangkiet.server;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PChangePasswordRequest;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionsResponse;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.service.EmployeeService;
import dev.ngdangkiet.service.PositionService;
import dev.ngdangkiet.service.UserService;
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
    private final PositionService positionService;
    private final UserService userService;

    // Employee
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
    public void getEmployeeByEmail(StringValue request, StreamObserver<PEmployeeResponse> responseObserver) {
        PEmployeeResponse response = employeeService.getEmployeeByEmail(request);
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

    // Position
    @Override
    public void createOrUpdatePosition(PPosition request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = positionService.createOrUpdatePosition(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPositionById(Int64Value request, StreamObserver<PPositionResponse> responseObserver) {
        PPositionResponse response = positionService.getPositionById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPositions(Empty request, StreamObserver<PPositionsResponse> responseObserver) {
        PPositionsResponse response = positionService.getPositions();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePositionById(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = positionService.deletePositionById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // User Account
    @Override
    public void changePassword(PChangePasswordRequest request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = userService.changePassword(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
