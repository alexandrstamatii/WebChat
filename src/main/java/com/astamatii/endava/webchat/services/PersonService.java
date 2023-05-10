package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.repositories.PersonRepository;
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
    public Person updateUser(Person updatedUser) {
        updatedUser.setUpdatedAt(ZonedDateTime.now());

        return personRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        personRepository.deleteByUsername(username);
    }

    @Transactional
    public void deleteUser(Person user) {
        personRepository.delete(user);
    }

    public boolean passwordCheck(String enteredPassword, String encryptedPassword) {
        return passwordEncoder.matches(enteredPassword, encryptedPassword);
    }
}
