package com.astamatii.endava.webchat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    private String usernameOrEmail;

    @NotNull
    private String password;
}
