package com.astamatii.endava.webchat.utils.validators;

import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.EmailExistsException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements
        ConstraintValidator<UniqueEmailConstraint, String> {
    private final PersonService personService;

    @Override
    public void initialize(UniqueEmailConstraint uniqueEmail) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        try {
            personService.findUserExistenceByEmail(email);
        } catch (EmailExistsException e) {
            return false;
        }
        return true;
    }

}
