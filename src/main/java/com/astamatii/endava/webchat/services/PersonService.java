package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.dto.helpers.ProfileDtoNotBlankNotNullFlags;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.repositories.PersonRepository;
import com.astamatii.endava.webchat.security.PersonDetailsService;
import com.astamatii.endava.webchat.utils.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonDetailsService personDetailsService;

    @Transactional
    public Person findUserByUsername(String username) {
        return personRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public Person findUserByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
    }

    @Transactional
    public Person findUserById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Transactional
    public void findUserExistenceByUsername(String username) {
        if (personRepository.findByUsername(username).isPresent()) throw new UsernameExistsException(username);
    }

    @Transactional
    public void findUserExistenceByEmail(String email) {
        if (personRepository.findByEmail(email).isPresent()) throw new EmailExistsException(email);
    }

    @Transactional
    public void registerUser(Person user) {
        findUserExistenceByUsername(user.getUsername());
        findUserExistenceByEmail(user.getEmail());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        personRepository.save(user);
    }

    @Transactional
    public void updateUser(Person updatedUser) {
        personRepository.save(updatedUser);
        personDetailsService.updateUserDetails(updatedUser);
    }

    @Transactional
    public void deleteCurrentUser() {
        personRepository.delete(getCurrentUser());
    }

    public boolean isNotBlank(String value) {
        return !value.isBlank();
    }

    public boolean isNotNull(Object obj) {
        return obj != null;
    }

    public boolean isNotEqual(String value1, String value2) {
        return !value1.equals(value2);
    }

    public ProfileDtoNotBlankNotNullFlags profileDtoNotBlankNotNullFlags(ProfileDto profileDto) {
        Person currentUser = getCurrentUser();
        String username = profileDto.getUsername();
        String email = profileDto.getEmail();

        return new ProfileDtoNotBlankNotNullFlags(
                isNotBlank(profileDto.getName()),
                isNotBlank(username) && isNotEqual(currentUser.getUsername(), username),
                isNotBlank(email) && isNotEqual(currentUser.getEmail(), email),
                isNotBlank(profileDto.getPassword()),
                isNotNull(profileDto.getDob()),
                isNotBlank(profileDto.getTextColor()),
                isNotNull(profileDto.getTheme()),
                isNotNull(profileDto.getCity()),
                isNotNull(profileDto.getLanguage())
        );
    }

    public Person prepareUpdatedUser(ProfileDto profileDto, ProfileDtoNotBlankNotNullFlags profileDtoNotBlankNotNullFlags) {
        Person currentUserClone = getCurrentUser();

        //Blank and Empty String and null fields, will be replaced by current values
        if (profileDtoNotBlankNotNullFlags.getUsername()) {
            String username = profileDto.getUsername();
            findUserExistenceByUsername(username);
            currentUserClone.setUsername(username);
        }
        if (profileDtoNotBlankNotNullFlags.getEmail()) {
            String email = profileDto.getEmail();
            findUserExistenceByEmail(email);
            currentUserClone.setEmail(email);
        }

        if (profileDtoNotBlankNotNullFlags.getPassword())
            currentUserClone.setPassword(passwordEncoder.encode(profileDto.getPassword()));
        if (profileDtoNotBlankNotNullFlags.getName()) currentUserClone.setName(profileDto.getName());
        if (profileDtoNotBlankNotNullFlags.getTextColor()) currentUserClone.setTextColor(profileDto.getTextColor());
        if (profileDtoNotBlankNotNullFlags.getDob()) currentUserClone.setDob(profileDto.getDob());
        if (profileDtoNotBlankNotNullFlags.getTheme()) currentUserClone.setTheme(profileDto.getTheme());
        if (profileDtoNotBlankNotNullFlags.getCity()) currentUserClone.setCity(profileDto.getCity());
        if (profileDtoNotBlankNotNullFlags.getLanguage()) currentUserClone.setLanguage(profileDto.getLanguage());

        currentUserClone.setUpdatedAt(ZonedDateTime.now());

        return currentUserClone;
    }

    public boolean passwordCheck(String enteredPassword) {
        return passwordEncoder.matches(enteredPassword, getCurrentUser().getPassword());
    }


    public Person getCurrentUser() {
        return findUserByUsername(personDetailsService.getCurrentUsername());
    }
}
