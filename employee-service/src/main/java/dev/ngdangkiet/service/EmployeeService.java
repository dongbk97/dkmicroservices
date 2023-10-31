package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

public interface EmployeeService {

    Int64Value createOrUpdateEmployee(PEmployee pEmployee);

    PEmployeeResponse getEmployeeById(Int64Value employeeId);
}
