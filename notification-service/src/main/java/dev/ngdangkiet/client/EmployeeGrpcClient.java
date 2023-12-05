package dev.ngdangkiet.client;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/18/2023
 */

@Component
@RequiredArgsConstructor
public class EmployeeGrpcClient {

    @GrpcClient("grpc-employee-service")
    private final EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub;

    public PEmployeesResponse getEmployees(PGetEmployeesRequest request) {
        return employeeServiceBlockingStub.getEmployees(request);
    }

    public PEmployeeResponse getEmployeeById(Long employeeId) {
        return employeeServiceBlockingStub.getEmployeeById(Int64Value.of(employeeId));
    }
}
