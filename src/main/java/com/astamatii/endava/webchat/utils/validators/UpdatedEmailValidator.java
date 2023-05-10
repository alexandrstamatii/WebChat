package com.astamatii.endava.webchat.utils.validators;

import com.astamatii.endava.webchat.security.PersonDetailsService;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.EmailExistsException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatedEmailValidator implements
        ConstraintValidator<UpdatedEmailConstraint, String> {
    private final PersonService personService;
    private final PersonDetailsService personDetailsService;

    @Override
    public void initialize(UpdatedEmailConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext context) {

        if (personService.findUserByUsername(personDetailsService.getCurrentUsername()).getEmail().equals(email))
            return true;

        try {
            personService.findUserExistenceByEmail(email);
        } catch (EmailExistsException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addConstraintViolation();
            return false;
        }

        if (email.length() > 31) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The email can have maximum 30 characters")
                    .addConstraintViolation();
            return false;
        }

        if (!(email.matches("^[a-zA-Z0-9][^\\s]*@[^\\s]*[a-zA-Z0-9]$") || email.equals(""))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The email should look like this: your_email@email.com")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
