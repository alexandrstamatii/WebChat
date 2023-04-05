package com.astamatii.endava.webchat.utils.exceptions;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class PersonExceptionResponse {
    private String message;
    private ZonedDateTime timestamp;
}
