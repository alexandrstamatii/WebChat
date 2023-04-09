package com.astamatii.endava.webchat.utils.exceptions;

public class ChatRoomsDoNotExistException extends RuntimeException{
    public ChatRoomsDoNotExistException() {
        super("No chat rooms created yet");
    }

    public ChatRoomsDoNotExistException(String message) {
        super(message);
    }
}
