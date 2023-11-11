package dev.ngdangkiet.validation;

import dev.ngdangkiet.common.Translator;
import dev.ngdangkiet.constant.MessageConstant;
import dev.ngdangkiet.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author ngdangkiet
 * @since 11/8/2023
 */

@RequiredArgsConstructor
public class GenderValidator implements ConstraintValidator<ValidationGender, String> {

    private final Translator translator;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = Arrays.stream(Gender.values()).anyMatch(g -> g.toString().equalsIgnoreCase(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(translator.translate(MessageConstant.Employee.INVALID_GENDER, null)).addConstraintViolation();
        }

        return isValid;
    }
}
