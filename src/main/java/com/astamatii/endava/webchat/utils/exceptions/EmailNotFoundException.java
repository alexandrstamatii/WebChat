package com.astamatii.endava.webchat.utils.exceptions;

public class EmailNotFoundException extends ResourceNotFoundException {
    public EmailNotFoundException() {
        super("User with this email was not found");
    }
}
