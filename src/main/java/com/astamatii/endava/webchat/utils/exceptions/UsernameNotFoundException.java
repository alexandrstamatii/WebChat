package com.astamatii.endava.webchat.utils.exceptions;

public class UsernameNotFoundException extends ResourceNotFoundException {
    public UsernameNotFoundException() {
        super("User with this username was not found");
    }
}
