package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Theme;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class ProfileDto {

    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    private String name;

    @Size(min = 3, max = 15, message = "The username must be between 3 and 15 letters in length")
    private String username;

    @Email(message = "The email should look like this: your_email@email.com")
    private String email;

    private String password;

    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    private String textColor;

    private Theme theme;

    private City city;

    private Language language;

}
