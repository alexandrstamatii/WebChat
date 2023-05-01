package com.astamatii.endava.webchat.utils.exceptions;

public class UnsuccessfulPasswordCheckException extends RuntimeException {
    public UnsuccessfulPasswordCheckException() {
        super("Password Verification Failed");
    }
}
