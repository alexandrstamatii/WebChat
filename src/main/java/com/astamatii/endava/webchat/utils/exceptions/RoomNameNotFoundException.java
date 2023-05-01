package com.astamatii.endava.webchat.utils.exceptions;

public class RoomNameNotFoundException extends ResourceNotFoundException{
    public RoomNameNotFoundException(String roomName) {
        super("'" + roomName + "' room was not found");
    }
}
