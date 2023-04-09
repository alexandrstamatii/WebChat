package com.astamatii.endava.webchat.utils.exceptions;

public class PersonEmailNotFoundException extends PersonNotFoundException{
    public PersonEmailNotFoundException() {
        super("Person with this email not found");
    }
}
