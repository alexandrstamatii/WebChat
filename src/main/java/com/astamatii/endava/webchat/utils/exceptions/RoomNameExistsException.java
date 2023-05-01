package com.astamatii.endava.webchat.utils.exceptions;

public class RoomNameExistsException extends ResourceExistsException {
    public RoomNameExistsException(String roomName) {
        super("'" + roomName + "' room name is already taken");
    }
}
