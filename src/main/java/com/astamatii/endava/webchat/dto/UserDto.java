package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.Theme;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserDto {

    private String language;

    @NotNull
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 letters in length")
    private String username;

    @NotNull
    @Email(message = "The email should look like this: your_email@email.com")
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String verifyPassword;

    //@DateTimeFormat(pattern = "dd.MM.yyyy") //throws DateTimeParseException ("Date format should be dd.MM.yyyy")
    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    private String textColor;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Theme theme;

    private String country;

    private String city;
}
