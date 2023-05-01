package com.astamatii.endava.webchat.utils.exceptions;

public class UsernameExistsException extends ResourceExistsException {
    public UsernameExistsException(String username) {
        super("'" + username + "' username is already taken");
    }
}
