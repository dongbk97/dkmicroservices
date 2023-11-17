package dev.ngdangkiet.payload.request.employee;

import dev.ngdangkiet.validation.ValidationGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
public class EmployeeRequest implements Serializable {

    private Long departmentId;
    @NotBlank(message = "FullName shouldn't be null or empty!")
    private String fullName;
    @Email(message = "Invalid email!")
    private String email;
    private Integer age;
    @ValidationGender
    private String gender;
    private Long positionId;
}
