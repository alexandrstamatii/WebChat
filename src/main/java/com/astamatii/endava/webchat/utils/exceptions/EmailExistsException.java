package com.astamatii.endava.webchat.utils.exceptions;

public class EmailExistsException extends ResourceExistsException {
    public EmailExistsException() {
        super("This email is already taken");
    }
}
