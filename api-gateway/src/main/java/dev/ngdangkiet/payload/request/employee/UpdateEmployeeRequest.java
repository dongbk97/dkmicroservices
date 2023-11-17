package dev.ngdangkiet.payload.request.employee;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

@Getter
@Setter
public class UpdateEmployeeRequest extends EmployeeRequest {

    private Long id;
}
