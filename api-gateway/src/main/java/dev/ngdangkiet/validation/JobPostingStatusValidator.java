package dev.ngdangkiet.validation;

import dev.ngdangkiet.enums.recruitment.JobPostingStatus;
import dev.ngdangkiet.validation.annotation.ValidationJobPostingStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

public class JobPostingStatusValidator implements ConstraintValidator<ValidationJobPostingStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return JobPostingStatus.isValidInput(value);
    }
}
