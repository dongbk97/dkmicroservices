package dev.ngdangkiet.payload.response.employee;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Getter
@Setter
public class EmployeeCommonResponse implements Serializable {

    private Long id;
    private String fullName;
    private String email;
    private Integer age;
    private String gender;
    private String birthDay;
}
