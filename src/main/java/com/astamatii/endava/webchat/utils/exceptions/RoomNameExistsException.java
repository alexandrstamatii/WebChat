package com.astamatii.endava.webchat.utils.exceptions;

public class RoomNameExistsException extends ResourceExistsException {
    public RoomNameExistsException() {
        super("This room name  is already taken");
    }
}
