package dev.ngdangkiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/8/2023
 */

@AllArgsConstructor
@Getter
public enum Position {

    DEVELOPER(1L, "DEVELOPER"),
    QA(2L, "QA"),
    TECH_LEAD(3L, "TECH_LEAD"),
    PROJECT_MANAGER(4L, "PROJECT_MANAGER");

    private final Long id;
    private final String name;

    public static Position of(Long id) {
        return Arrays.stream(Position.values())
                .filter(p -> p.id.equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
}
