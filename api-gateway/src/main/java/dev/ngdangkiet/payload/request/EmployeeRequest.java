package dev.ngdangkiet.payload.request;

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
    private String fullName;
    private String email;
    private Integer age;
    private Long positionId;
}
