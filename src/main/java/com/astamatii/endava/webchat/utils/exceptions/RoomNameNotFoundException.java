package com.astamatii.endava.webchat.utils.exceptions;

public class RoomNameNotFoundException extends ResourceNotFoundException{
    public RoomNameNotFoundException() {
        super("This room name was not found");
    }
}
