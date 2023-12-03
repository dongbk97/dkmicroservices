package dev.ngdangkiet.enums.tracking;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

public enum Action {

    CHANGE_PASSWORD,
    CHECK_IN_OUT;

    public static Action of(String value) {
        return Arrays.stream(Action.values())
                .filter(a -> a.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Action!"));
    }
}
