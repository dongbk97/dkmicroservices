package dev.ngdangkiet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@AllArgsConstructor
@Getter
public enum PositionConstant {

    DEVELOPER(1L, "Developer"),
    QA(2L, "QA"),
    TECH_LEAD(3L, "Tech lead"),
    PROJECT_MANAGER(4L, "Project manager");

    private final Long id;
    private final String name;

    public static PositionConstant of(Long id) {
        return Arrays.stream(PositionConstant.values())
                .filter(p -> p.id.equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
}
