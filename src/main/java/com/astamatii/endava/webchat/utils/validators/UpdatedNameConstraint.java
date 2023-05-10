package com.astamatii.endava.webchat.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdatedNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdatedNameConstraint {
    String message() default "The name length must be between 3 and 50 letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
