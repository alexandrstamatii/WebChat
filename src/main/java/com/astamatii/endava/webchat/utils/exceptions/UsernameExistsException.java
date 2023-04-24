package com.astamatii.endava.webchat.utils.exceptions;

public class UsernameExistsException extends ResourceExistsException {
    public UsernameExistsException() {
        super("This username is already taken");
    }
}
