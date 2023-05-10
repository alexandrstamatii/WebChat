package com.astamatii.endava.webchat.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UpdatedPasswordValidator
        implements
        ConstraintValidator<UpdatedPasswordConstraint, String> {

    @Override
    public void initialize(UpdatedPasswordConstraint constraintAnnotation) {
    }
    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext context) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$") || password.equals("");
    }

}
