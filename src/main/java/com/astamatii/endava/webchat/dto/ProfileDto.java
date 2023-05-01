package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Theme;
import com.astamatii.endava.webchat.utils.validators.UniqueEmailConstraint;
import com.astamatii.endava.webchat.utils.validators.UniqueUsernameConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class ProfileDto {

    @NotBlank(message = "The name should not be blank or empty")
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    private String name;

    @UniqueUsernameConstraint
    @NotBlank(message = "The username should not be blank or empty")
    @Size(min = 3, max = 15, message = "The username must be between 3 and 15 letters in length")
    private String username;

    @UniqueEmailConstraint
    @NotBlank(message = "The name should not be blank (with whitespaces at both ends) or empty")
    @Size(max = 30, message = "The email must have less or 30 letters in length")
    @Email(message = "The email should look like this: your_email@email.com")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.")
    private String password;

//    @DateTimeFormat(pattern = "dd.MM.yyyy") //throws DateTimeParseException ("Date format should be dd.MM.yyyy")
    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    @NotBlank(message = "The name should not be blank (with whitespaces at both ends) or empty")
    private String textColor;

    private Theme theme;
    private City city;
    private Language language;
    private String passwordCheck;

}
