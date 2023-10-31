package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentResponse;
import dev.ngdangkiet.dkmicroservices.department.service.DepartmentServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@GrpcService
public class DepartmentServiceGrpcServer extends DepartmentServiceGrpc.DepartmentServiceImplBase {

    @Override
    public void createOrUpdateDepartment(PDepartment request, StreamObserver<Int64Value> responseObserver) {
        super.createOrUpdateDepartment(request, responseObserver);
    }

    @Override
    public void getDepartmentById(Int64Value request, StreamObserver<PDepartmentResponse> responseObserver) {
        super.getDepartmentById(request, responseObserver);
    }
}
