package com.astamatii.endava.webchat.utils.exceptions;

public class ChatRoomsNotFoundException extends ResourceNotFoundException{
    public ChatRoomsNotFoundException() {
        super("No chat rooms created yet");
    }

    public ChatRoomsNotFoundException(String message) {
        super(message);
    }
}
