package com.astamatii.endava.webchat.utils.exceptions;

public class EmailNotFoundException extends ResourceNotFoundException {
    public EmailNotFoundException(String email) {
        super("User with '" + email + "' email was not found");
    }
}
