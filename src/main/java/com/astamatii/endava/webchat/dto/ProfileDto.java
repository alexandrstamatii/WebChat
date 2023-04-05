package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.Theme;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {

    @NotNull
    private Long id;
    @NotNull
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    private String name;
    private LocalDate dob;

    private String language;

    private String country;

    private String city;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    private String textColor;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Theme theme;

}
