package dev.ngdangkiet.payload.request.employee;

import dev.ngdangkiet.validation.ValidationGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
public class UpsertEmployeeRequest implements Serializable {

    @NotBlank(message = "FullName shouldn't be null or empty!")
    private String fullName;
    @Email(message = "Invalid email!")
    private String email;
    private Integer age;
    @ValidationGender
    private String gender;
    @NotBlank(message = "Birthday shouldn't be null or empty!")
    private String birthDay;
    @NotNull(message = "Department shouldn't be null or empty!")
    private Department department;
    @NotNull(message = "Position shouldn't be null or empty!")
    private Position position;

    @Getter
    @Setter
    public static class Department {

        @NotNull(message = "Department Id shouldn't be null or empty!")
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    public static class Position {

        @NotNull(message = "Position Id shouldn't be null or empty!")
        private Long id;
        private String name;
    }
}
