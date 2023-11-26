package dev.ngdangkiet.client;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionsResponse;
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

    public PEmployeeResponse getEmployeeById(Long employeeId) {
        return employeeServiceBlockingStub.getEmployeeById(Int64Value.of(employeeId));
    }

    public PEmployee getEmployeeByEmail(String email) {
        PEmployeeResponse response = employeeServiceBlockingStub.getEmployeeByEmail(StringValue.of(email));
        if (ErrorHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }

    public PEmployeesResponse getEmployees(PGetEmployeesRequest request) {
        return employeeServiceBlockingStub.getEmployees(request);
    }

    public EmptyResponse deleteEmployeeById(Long employeeId) {
        return employeeServiceBlockingStub.deleteEmployeeById(Int64Value.of(employeeId));
    }

    public long createOrUpdatePosition(PPosition request) {
        return employeeServiceBlockingStub.createOrUpdatePosition(request).getValue();
    }

    public PPositionResponse getPositionById(Long positionId) {
        return employeeServiceBlockingStub.getPositionById(Int64Value.of(positionId));
    }

    public PPositionsResponse getPositions() {
        return employeeServiceBlockingStub.getPositions(Empty.newBuilder().build());
    }

    public EmptyResponse deletePositionById(Long positionId) {
        return employeeServiceBlockingStub.deletePositionById(Int64Value.of(positionId));
    }
}
