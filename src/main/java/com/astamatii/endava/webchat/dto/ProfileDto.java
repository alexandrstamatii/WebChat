package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Theme;
import com.astamatii.endava.webchat.utils.validators.UpdatedEmailConstraint;
import com.astamatii.endava.webchat.utils.validators.UpdatedNameConstraint;
import com.astamatii.endava.webchat.utils.validators.UpdatedPasswordConstraint;
import com.astamatii.endava.webchat.utils.validators.UpdatedUsernameConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {

    @UpdatedNameConstraint
    private String name;

    @UpdatedUsernameConstraint
    private String username;

    @UpdatedEmailConstraint
    private String email;

    @UpdatedPasswordConstraint
    private String password;

//    @DateTimeFormat(pattern = "dd.MM.yyyy") //throws DateTimeParseException ("Date format should be dd.MM.yyyy")
    private LocalDate dob;

    private String textColor;

    private Theme theme;
    private City city;
    private Language language;
    private String passwordCheck;

}
