package dev.ngdangkiet.payload.request.employee;

import dev.ngdangkiet.validation.ValidationPassword;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */


@Getter
@Setter
public class CreateEmployeeRequest extends EmployeeRequest {

    @ValidationPassword
    private String password;
}
