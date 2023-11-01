package dev.ngdangkiet.client;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentResponse;
import dev.ngdangkiet.dkmicroservices.department.service.DepartmentServiceGrpc;
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
public class DepartmentGrpcClient {

    @GrpcClient("grpc-department-service")
    private final DepartmentServiceGrpc.DepartmentServiceBlockingStub departmentServiceBlockingStub;

    public long createOrUpdateDepartment(PDepartment department) {
        Int64Value response = departmentServiceBlockingStub.createOrUpdateDepartment(department);
        return response.getValue();
    }

    public PDepartment getDepartmentById(Long departmentId) {
        PDepartmentResponse response = departmentServiceBlockingStub.getDepartmentById(Int64Value.of(departmentId));
        if (ErrorHelper.isSuccess(response.getCode())) {
            return response.getData();
        }
        return null;
    }
}
