package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

public interface EmployeeService {

    Int64Value createOrUpdateEmployee(PEmployee pEmployee);

    PEmployeeResponse getEmployeeById(Int64Value employeeId);

    PEmployeesResponse getEmployees(PGetEmployeesRequest request);

    EmptyResponse deleteEmployeeById(Int64Value request);
}
