package com.astamatii.endava.webchat.utils.exceptions;

public class EmailExistsException extends ResourceExistsException {
    public EmailExistsException(String email) {
        super("'" + email + "' email is already taken");
    }
}
