package dev.ngdangkiet.client;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentsResponse;
import dev.ngdangkiet.dkmicroservices.department.service.DepartmentServiceGrpc;
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

    public PDepartmentResponse getDepartmentById(Long departmentId) {
        return departmentServiceBlockingStub.getDepartmentById(Int64Value.of(departmentId));
    }

    public PDepartmentsResponse getDepartments() {
        return departmentServiceBlockingStub.getDepartments(Empty.newBuilder().build());
    }

    public EmptyResponse deleteDepartmentById(Long departmentId) {
        return departmentServiceBlockingStub.deleteDepartmentById(Int64Value.of(departmentId));
    }
}
