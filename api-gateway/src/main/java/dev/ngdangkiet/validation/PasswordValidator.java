package dev.ngdangkiet.validation;

import dev.ngdangkiet.common.Translator;
import dev.ngdangkiet.constant.MessageConstant;
import dev.ngdangkiet.constant.ValidateConstant;
import dev.ngdangkiet.validation.annotation.ValidationPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author ngdangkiet
 * @since 11/10/2023
 */

@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidationPassword, String> {

    private final Translator translator;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = StringUtils.hasText(value)
                && value.length() >= ValidateConstant.Password.MIN_SIZE
                && value.length() <= ValidateConstant.Password.MAX_SIZE
                && Pattern.matches(ValidateConstant.Password.PATTERN_PASSWORD, value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(translator.translate(MessageConstant.Employee.INVALID_PASSWORD, null)).addConstraintViolation();
        }

        return isValid;
    }
}
