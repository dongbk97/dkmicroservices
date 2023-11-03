package dev.ngdangkiet.server;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentsResponse;
import dev.ngdangkiet.dkmicroservices.department.service.DepartmentServiceGrpc;
import dev.ngdangkiet.service.DepartmentService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@GrpcService
@RequiredArgsConstructor
public class DepartmentServiceGrpcServer extends DepartmentServiceGrpc.DepartmentServiceImplBase {

    private final DepartmentService departmentService;

    @Override
    public void createOrUpdateDepartment(PDepartment request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = departmentService.createOrUpdateDepartment(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDepartmentById(Int64Value request, StreamObserver<PDepartmentResponse> responseObserver) {
        PDepartmentResponse response = departmentService.getDepartmentById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDepartments(Empty request, StreamObserver<PDepartmentsResponse> responseObserver) {
        PDepartmentsResponse response = departmentService.getDepartments();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteDepartmentById(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = departmentService.deleteDepartmentById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
