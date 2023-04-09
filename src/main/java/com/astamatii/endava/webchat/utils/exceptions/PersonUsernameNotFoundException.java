package com.astamatii.endava.webchat.utils.exceptions;

public class PersonUsernameNotFoundException extends PersonNotFoundException{
    public PersonUsernameNotFoundException() {
        super("Person with this username not found");
    }
}
