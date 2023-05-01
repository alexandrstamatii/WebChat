package com.astamatii.endava.webchat.utils.exceptions;

public class IdNotFoundException extends ResourceNotFoundException {
    public IdNotFoundException(Long id) {
        super("User with id " + id + " was not found");
    }
}
