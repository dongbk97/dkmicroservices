package dev.ngdangkiet.payload.response.department;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
public class DepartmentResponse {

    private Long id;
    private String name;
    private List<Employee> employees;

    @Getter
    @Setter
    public static class Employee {
        private Long id;
        private String fullName;
        private String email;
    }
}
