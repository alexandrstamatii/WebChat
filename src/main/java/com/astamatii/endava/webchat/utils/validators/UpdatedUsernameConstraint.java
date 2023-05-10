package com.astamatii.endava.webchat.utils.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdatedUsernameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdatedUsernameConstraint {
    String message() default "This username does not meet the requirements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}