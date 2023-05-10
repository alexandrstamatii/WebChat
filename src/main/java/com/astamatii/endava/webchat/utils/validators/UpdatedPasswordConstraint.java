package com.astamatii.endava.webchat.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdatedPasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdatedPasswordConstraint {
    String message() default "Password must contain: at least 8 characters, one uppercase letter, one lowercase letter, one digit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
