package com.astamatii.endava.webchat.utils.validators;

import com.astamatii.endava.webchat.security.PersonDetailsService;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.EmailExistsException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatedNameValidator implements
        ConstraintValidator<UpdatedNameConstraint, String> {

    @Override
    public void initialize(UpdatedNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name,
                           ConstraintValidatorContext context) {
        int length = name.length();
        return (length >= 3 && length < 51) || name.equals("");
    }
}
