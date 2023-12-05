package dev.ngdangkiet.validation.annotation;

import dev.ngdangkiet.validation.DeadlineValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DeadlineValidator.class)
public @interface ValidationDeadline {

    String message() default "Invalid deadline. Shouldn't be null or empty and after date now!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
