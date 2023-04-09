package com.astamatii.endava.webchat.utils.exceptions;

public class RoomNameExistsException extends RoomNotCreatedException {
    public RoomNameExistsException() {
        super("Room with such name already exists");
    }
}
