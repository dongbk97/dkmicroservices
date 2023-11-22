package dev.ngdangkiet.payload.response.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
@Builder
public class EmployeeResponse {

    private Long id;
    private String fullName;
    private String email;
    private Integer age;
    private Position position;
    private Department department;
    private String birthDay;

    @Getter
    @Setter
    @Builder
    public static class Position {

        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @Builder(setterPrefix = "set")
    public static class Department {

        private Long id;
        private String name;
    }
}
