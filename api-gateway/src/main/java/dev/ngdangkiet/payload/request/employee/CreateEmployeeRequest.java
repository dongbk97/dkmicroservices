package dev.ngdangkiet.payload.request.employee;

import dev.ngdangkiet.validation.annotation.ValidationPassword;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */


@Getter
@Setter
public class CreateEmployeeRequest extends UpsertEmployeeRequest {

    @ValidationPassword
    private String password;
}
