package dev.ngdangkiet.enums.payroll;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Getter
@AllArgsConstructor
public enum TaxType {

    PERSONAL_INCOME_TAX("PIT");

    private final String shortName;

    public static TaxType of(String value) {
        return Arrays.stream(TaxType.values())
                .filter(i -> i.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tax type is invalid!"));
    }
}
