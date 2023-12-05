package dev.ngdangkiet.validation;

import dev.ngdangkiet.constant.ValidateConstant;
import dev.ngdangkiet.validation.annotation.ValidationPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<ValidationPhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(ValidateConstant.PhoneNumber.PHONE_NUMBER_PATTERN, value);
    }
}
