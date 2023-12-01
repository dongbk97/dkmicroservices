package dev.ngdangkiet.payload.response.employee;

import dev.ngdangkiet.payload.response.department.DepartmentResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Getter
@Setter
public class EmployeeDetailResponse extends EmployeeCommonResponse {

    private DepartmentResponse department;
    private PositionResponse position;
}
