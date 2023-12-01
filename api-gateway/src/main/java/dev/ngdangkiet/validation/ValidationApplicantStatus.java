package dev.ngdangkiet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ApplicantStatusValidator.class)
public @interface ValidationApplicantStatus {

    String message() default "Invalid status!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}