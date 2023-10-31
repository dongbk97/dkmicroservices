package dev.ngdangkiet.payload.response;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.Position;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String fullName;
    private String email;
    private Integer age;
    private Position position;
    private Department department;

    @Getter
    @Setter
    public static class Position {

        private Long id;
        private String name;
    }

    @Getter
    @Setter
    public static class Department {

        private Long id;
        private String name;
    }
}
