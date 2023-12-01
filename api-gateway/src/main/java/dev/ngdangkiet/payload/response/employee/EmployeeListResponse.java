package dev.ngdangkiet.payload.response.employee;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Getter
@Setter
public class EmployeeListResponse extends EmployeeCommonResponse {

    private Long departmentId;
    private Long positionId;
}
