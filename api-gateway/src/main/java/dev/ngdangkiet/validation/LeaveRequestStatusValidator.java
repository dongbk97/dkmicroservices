package dev.ngdangkiet.validation;

import dev.ngdangkiet.enums.attendance.LeaveRequestStatus;
import dev.ngdangkiet.validation.annotation.ValidationLeaveRequestStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

public class LeaveRequestStatusValidator implements ConstraintValidator<ValidationLeaveRequestStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return LeaveRequestStatus.isValidLeaveRequestStatus(value);
    }
}
