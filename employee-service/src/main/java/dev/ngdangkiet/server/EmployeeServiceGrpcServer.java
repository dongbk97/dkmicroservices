package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@GrpcService
public class EmployeeServiceGrpcServer extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Override
    public void createOrUpdateEmployee(PEmployee request, StreamObserver<Int64Value> responseObserver) {
        super.createOrUpdateEmployee(request, responseObserver);
    }

    @Override
    public void getEmployeeById(Int64Value request, StreamObserver<PEmployeeResponse> responseObserver) {
        super.getEmployeeById(request, responseObserver);
    }
}
