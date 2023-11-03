package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentsResponse;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

public interface DepartmentService {

    Int64Value createOrUpdateDepartment(PDepartment pDepartment);

    PDepartmentResponse getDepartmentById(Int64Value departmentId);

    PDepartmentsResponse getDepartments();

    EmptyResponse deleteDepartmentById(Int64Value departmentId);
}
