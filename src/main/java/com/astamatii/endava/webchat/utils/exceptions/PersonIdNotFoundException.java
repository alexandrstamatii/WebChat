package com.astamatii.endava.webchat.utils.exceptions;

public class PersonIdNotFoundException extends PersonNotFoundException{
    public PersonIdNotFoundException() {
        super("Person with this id not found");
    }
}
