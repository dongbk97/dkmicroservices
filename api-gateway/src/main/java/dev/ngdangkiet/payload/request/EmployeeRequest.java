package dev.ngdangkiet.payload.request;

import dev.ngdangkiet.validation.ValidationGender;
import dev.ngdangkiet.validation.ValidationPassword;
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

    private Long id;
    private Long departmentId;
    @NotBlank(message = "FullName shouldn't be null or empty!")
    private String fullName;
    @Email(message = "Invalid email!")
    private String email;
    @ValidationPassword
    private String password;
    private Integer age;
    @ValidationGender
    private String gender;
    private Long positionId;
}
