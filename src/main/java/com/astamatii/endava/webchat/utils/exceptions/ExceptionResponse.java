package com.astamatii.endava.webchat.utils.exceptions;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private ZonedDateTime timestamp;
}
