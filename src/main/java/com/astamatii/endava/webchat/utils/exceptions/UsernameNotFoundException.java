package com.astamatii.endava.webchat.utils.exceptions;

public class UsernameNotFoundException extends ResourceNotFoundException {
    public UsernameNotFoundException(String username) {
        super("User with '" + username + "' username was not found");
    }
}
