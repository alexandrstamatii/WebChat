package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Theme;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {

    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    @NotBlank(message = "The name should not be blank")
    @NotEmpty(message = "The name should not be empty")
    private String name;

    @Size(min = 3, max = 15, message = "The username must be between 3 and 15 letters in length")
    @NotBlank(message = "The username should not be blank")
    @NotEmpty(message = "The username should not be empty")
    private String username;

    @Email(message = "The email should look like this: your_email@email.com")
    @NotBlank(message = "email should not be blank")
    @NotEmpty(message = "email should not be empty")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.")
    private String password;

    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    private String textColor;

    private Theme theme;

    private City city;

    private Language language;

}
