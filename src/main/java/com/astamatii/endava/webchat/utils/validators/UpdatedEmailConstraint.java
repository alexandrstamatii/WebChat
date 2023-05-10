package com.astamatii.endava.webchat.utils.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdatedEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdatedEmailConstraint {
    String message() default "This email does not meet the requirements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}