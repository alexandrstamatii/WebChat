package com.astamatii.endava.webchat.utils.mappers;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.models.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    private final PasswordEncoder passwordEncoder;

    public Person mapToPerson(ProfileDto profileDto) {
        Person user = new Person();
        user.setName(profileDto.getName());
        user.setUsername(profileDto.getUsername());
        user.setEmail(profileDto.getEmail());
        user.setPassword(profileDto.getPassword());
        user.setDob(profileDto.getDob());
        user.setTextColor(profileDto.getTextColor());
        user.setTheme(profileDto.getTheme());
        user.setCity(profileDto.getCity());
        user.setLanguage(profileDto.getLanguage());
        return user;
    }

    public ProfileDto mapToProfileDto(Person user) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setName(user.getName());
        profileDto.setUsername(user.getUsername());
        profileDto.setEmail(user.getEmail());
        profileDto.setPassword(user.getPassword());
        profileDto.setDob(user.getDob());
        profileDto.setTextColor(user.getTextColor());
        profileDto.setTheme(user.getTheme());
        profileDto.setCity(user.getCity());
        profileDto.setLanguage(user.getLanguage());
        profileDto.setPasswordCheck("");
        return profileDto;
    }

    public void updatePerson(Person user, ProfileDto profileDto) {
        String name = profileDto.getName(),
                username = profileDto.getUsername(),
                email = profileDto.getEmail(),
                password = profileDto.getPassword(),
                textColor = profileDto.getTextColor();
        LocalDate dob = profileDto.getDob();
        Theme theme = profileDto.getTheme();
        City city = profileDto.getCity();
        Language language = profileDto.getLanguage();

        if (!(name.isBlank() || name.equals(user.getName())))
            user.setName(name);
        if (!(username.isBlank() || username.equals(user.getUsername())))
            user.setUsername(username);
        if (!(email.isBlank() || email.equals(user.getEmail())))
            user.setEmail(email);
        if (!password.isBlank())
            user.setPassword(passwordEncoder.encode(password));
        if (dob != null)
            user.setDob(dob);
        if (!(textColor.isBlank() || textColor.equals(user.getTextColor())))
            user.setTextColor(textColor);
        if (theme != null)
            user.setTheme(theme);
        if (city != null)
            user.setCity(city);
        if (language != null)
            user.setLanguage(language);
    }

}
