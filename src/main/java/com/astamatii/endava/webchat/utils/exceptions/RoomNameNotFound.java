package com.astamatii.endava.webchat.utils.exceptions;

public class RoomNameNotFound extends RoomNotFoundException{
    public RoomNameNotFound() {
        super("Person with this username not found");
    }
}
