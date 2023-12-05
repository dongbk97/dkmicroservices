package dev.ngdangkiet.validation.annotation;

import dev.ngdangkiet.validation.LeaveRequestStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LeaveRequestStatusValidator.class)
public @interface ValidationLeaveRequestStatus {

    String message() default "Invalid leave request type!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
