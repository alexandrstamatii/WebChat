package com.astamatii.endava.webchat.utils.exceptions;

public class PersonEmailExistsException extends PersonNotCreatedException{
    public PersonEmailExistsException() {
        super("User with such email already exists");
    }
}
