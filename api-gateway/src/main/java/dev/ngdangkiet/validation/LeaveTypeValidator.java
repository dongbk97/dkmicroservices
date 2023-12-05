package dev.ngdangkiet.validation;

import dev.ngdangkiet.enums.attendance.LeaveType;
import dev.ngdangkiet.validation.annotation.ValidationLeaveType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

public class LeaveTypeValidator implements ConstraintValidator<ValidationLeaveType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return LeaveType.isValidLeaveType(value);
    }
}
