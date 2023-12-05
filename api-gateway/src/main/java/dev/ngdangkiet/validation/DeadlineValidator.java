package dev.ngdangkiet.validation;

import dev.ngdangkiet.util.DateTimeUtil;
import dev.ngdangkiet.validation.annotation.ValidationDeadline;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

public class DeadlineValidator implements ConstraintValidator<ValidationDeadline, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return Objects.requireNonNull(DateTimeUtil.convert2Localdate(value)).isAfter(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
