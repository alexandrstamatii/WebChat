package com.astamatii.endava.webchat.utils.exceptions;

public class PersonUsernameExistsException extends PersonNotCreatedException{
    public PersonUsernameExistsException() {
        super("User with such username already exists");
    }
}
