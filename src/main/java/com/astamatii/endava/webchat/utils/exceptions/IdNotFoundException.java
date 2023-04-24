package com.astamatii.endava.webchat.utils.exceptions;

public class IdNotFoundException extends ResourceNotFoundException {
    public IdNotFoundException() {
        super("User with this id was not found");
    }
}
