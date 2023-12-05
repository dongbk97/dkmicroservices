package dev.ngdangkiet.validation.annotation;

import dev.ngdangkiet.validation.GenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ngdangkiet
 * @since 11/8/2023
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderValidator.class)
public @interface ValidationGender {

    String message() default "Invalid gender!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
