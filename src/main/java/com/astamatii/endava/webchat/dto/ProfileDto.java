package com.astamatii.endava.webchat.dto;

import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Theme;
import com.astamatii.endava.webchat.utils.validators.UpdatedEmailConstraint;
import com.astamatii.endava.webchat.utils.validators.UpdatedNameConstraint;
import com.astamatii.endava.webchat.utils.validators.UpdatedPasswordConstraint;
import com.astamatii.endava.webchat.utils.validators.UpdatedUsernameConstraint;
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
    @Size(max = 255, message = "The password can have maximum 255 characters")
    private String password;

    private LocalDate dob;

    private String textColor;

    private Theme theme;
    private City city;
    private Language language;
    private String passwordCheck;

}
