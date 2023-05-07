package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.dto.ProfileDto;
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
    public void updateUser(ProfileDto profileDto) {

        Person currentUser = getCurrentUser();

        //Blank and Empty String and null fields, will be replaced by current values

        if (!profileDto.getPassword().isBlank())
            currentUser.setPassword(passwordEncoder.encode(profileDto.getPassword()));
        if (!profileDto.getUsername().isBlank()) currentUser.setUsername(profileDto.getUsername());
        if (!profileDto.getEmail().isBlank()) currentUser.setEmail(profileDto.getEmail());
        if (!profileDto.getName().isBlank()) currentUser.setName(profileDto.getName());
        if (!profileDto.getTextColor().isBlank()) currentUser.setTextColor(profileDto.getTextColor());
        if (profileDto.getDob() != null) currentUser.setDob(profileDto.getDob());
        if (profileDto.getTheme() != null) currentUser.setTheme(profileDto.getTheme());
        if (profileDto.getCity() != null) currentUser.setCity(profileDto.getCity());
        if (profileDto.getLanguage() != null) currentUser.setLanguage(profileDto.getLanguage());

        currentUser.setUpdatedAt(ZonedDateTime.now());
        personRepository.save(currentUser);

        personDetailsService.updateUserDetails(currentUser);
    }

    @Transactional
    public void deleteCurrentUser() {
        personRepository.delete(getCurrentUser());
    }

    public boolean passwordCheck(String enteredPassword) {
        return passwordEncoder.matches(enteredPassword, getCurrentUser().getPassword());
    }

    public Person getCurrentUser() {
        return findUserByUsername(personDetailsService.getCurrentUsername());
    }
}
