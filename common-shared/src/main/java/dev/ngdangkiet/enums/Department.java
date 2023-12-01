package dev.ngdangkiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@AllArgsConstructor
@Getter
public enum Department {

    EXECUTIVE_LEADERSHIP(1L, "Executive Leadership"),
    ENGINEERING_AND_DEVELOPMENT(2L, "Engineering and Development"),
    DATA_AND_ANALYTICS(3L, "Data and Analytics"),
    IT_AND_SECURITY(4L, "IT and Security"),
    SALES_AND_MARKETING(5L, "Sales and Marketing"),
    RESEARCH_AND_DEVELOPMENT(6L, "Research and Development"),
    OPERATIONS_AND_SUPPORT(7L, "Operations and Support"),
    HIRING_DEPARTMENT(8L, "HR Department");

    private final Long id;
    private final String name;

    public static Department valueOf(Long id) {
        return Arrays.stream(Department.values())
                .filter(d -> d.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Department Id!"));
    }
}
