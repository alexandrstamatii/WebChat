package com.astamatii.endava.webchat.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class SignUpDto {

    @NotNull
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    private String name;
    @NotNull
    @Size(min = 3, max = 15, message = "The username must be between 3 and 15 letters in length")
    private String username;

    @NotNull
    @Email(message = "The email should look like this: your_email@email.com")
    private String email;

    @NotNull
    @Size(min = 8, max = 30, message = "The username must be between 8 and 30 letters in length")
    private String password;

}
