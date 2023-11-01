package dev.ngdangkiet.client;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.error.ErrorHelper;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Component
@RequiredArgsConstructor
public class EmployeeGrpcClient {

    @GrpcClient("grpc-employee-service")
    private final EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub;

    public long createOrUpdateEmployee(PEmployee employee) {
        Int64Value response = employeeServiceBlockingStub.createOrUpdateEmployee(employee);
        return response.getValue();
    }

    public PEmployee getEmployeeById(Long employeeId) {
        PEmployeeResponse response = employeeServiceBlockingStub.getEmployeeById(Int64Value.of(employeeId));
        if (ErrorHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }
}
