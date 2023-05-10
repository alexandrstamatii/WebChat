package com.astamatii.endava.webchat.utils.validators;

import com.astamatii.endava.webchat.security.PersonDetailsService;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.UsernameExistsException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatedUsernameValidator implements
        ConstraintValidator<UpdatedUsernameConstraint, String> {
    private final PersonService personService;
    private final PersonDetailsService personDetailsService;

    @Override
    public void initialize(UpdatedUsernameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username,
                           ConstraintValidatorContext context) {
        if(personDetailsService.getCurrentUsername().equals(username))
            return true;

        try {
            personService.findUserExistenceByUsername(username);
        } catch (UsernameExistsException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addConstraintViolation();
            return false;
        }

        int length = username.length();
        if ((length != 0 && length < 3) || length > 30 ) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The username must be between 3 and 30 letters in length")
                    .addConstraintViolation();
            return false;
        }

        if (!(username.matches("^[a-z][a-z0-9]*$") || username.equals(""))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The username must contain only lowercase letters and numbers, and start from a letter")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
