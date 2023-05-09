package com.astamatii.endava.webchat.dto.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

@Setter
@Getter
public class ProfileDtoNotBlankNotNullFlags {
    private Boolean name;
    private Boolean username;
    private Boolean email;
    private Boolean password;
    private Boolean dob;
    private Boolean textColor;
    private Boolean theme;
    private Boolean city;
    private Boolean language;
    private Boolean errorsAdded;
    private boolean anyChangedCredentials;

    public ProfileDtoNotBlankNotNullFlags() {
        this.name = false;
        this.username = false;
        this.email = false;
        this.password = false;
        this.dob = false;
        this.textColor = false;
        this.theme = false;
        this.city = false;
        this.language = false;
        this.errorsAdded = false;
        this.anyChangedCredentials = false;
    }

    public ProfileDtoNotBlankNotNullFlags(Boolean name, Boolean username, Boolean email, Boolean password, Boolean dob, Boolean textColor, Boolean theme, Boolean city, Boolean language, Boolean errorsAdded) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.textColor = textColor;
        this.theme = theme;
        this.city = city;
        this.language = language;
        this.errorsAdded = errorsAdded;
        this.anyChangedCredentials = username || email || password;
    }

    public ProfileDtoNotBlankNotNullFlags getErrorFlags(BindingResult bindingResult) {
        return new ProfileDtoNotBlankNotNullFlags(
                this.name && bindingResult.hasFieldErrors("name"),
                this.username && bindingResult.hasFieldErrors("username"),
                this.email && bindingResult.hasFieldErrors("email"),
                this.password && bindingResult.hasFieldErrors("password"),
                this.dob && bindingResult.hasFieldErrors("dob"),
                this.textColor && bindingResult.hasFieldErrors("textColor"),
                this.theme && bindingResult.hasFieldErrors("theme"),
                this.city && bindingResult.hasFieldErrors("city"),
                this.language && bindingResult.hasFieldErrors("language"),
                true
        );
    }

    public Boolean findAnyError(){
        return (this.name || this.username || this.email || this.password || this.dob
                || this.textColor || this.theme || this.city || this.language) && this.errorsAdded;
    }

}
