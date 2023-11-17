package dev.ngdangkiet.client;

import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PChangePasswordRequest;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

@Component
@RequiredArgsConstructor
public class UserGrpcClient {

    @GrpcClient("grpc-employee-service")
    private final EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub;

    public EmptyResponse changePassword(PChangePasswordRequest request) {
        return employeeServiceBlockingStub.changePassword(request);
    }
}
