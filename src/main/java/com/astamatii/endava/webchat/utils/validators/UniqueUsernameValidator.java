package com.astamatii.endava.webchat.utils.validators;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.UsernameExistsException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements
        ConstraintValidator<UniqueUsernameConstraint, String> {
    private final PersonService personService;

    @Override
    public void initialize(UniqueUsernameConstraint uniqueUsername) {
    }

    @Override
    public boolean isValid(String username,
                           ConstraintValidatorContext cxt) {
        try {
            personService.findUserExistenceByUsername(username);
        } catch (UsernameExistsException e) {
            return false;
        }
        return true;
    }

}
