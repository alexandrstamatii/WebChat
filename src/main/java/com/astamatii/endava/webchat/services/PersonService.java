package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.repositories.PersonRepository;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotCreatedException;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Person findById(Long id){
        Optional<Person> personOptional = personRepository.findById(id);

        return personOptional.orElseThrow(() -> new PersonNotFoundException("Person with this id not found"));
    }

    @Transactional
    public Person findByUsername(String username){
        Optional<Person> personOptional = personRepository.findByUsername(username);

        return personOptional.orElseThrow(() -> new PersonNotFoundException("Person with this username not found"));
    }

    @Transactional
    public Person findByEmail(String email){
        Optional<Person> personOptional = personRepository.findByEmail(email);

        return personOptional.orElseThrow(() -> new PersonNotFoundException("Person with this email not found"));
    }

    @Transactional
    public void updateProfile(Person person){
        Person updatedPerson = findById(person.getId());
        person.setUsername(updatedPerson.getUsername());
        person.setEmail(updatedPerson.getEmail());
        person.setPassword(updatedPerson.getPassword());
        personRepository.save(person);
    }

    @Transactional
    public void registerPerson(Person person) {
        verifyBeforePersist(person);
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        personRepository.save(person);
    }

    @Transactional
    public void updatePassword(Long id, String password) {
        Person updatedPerson = findById(id);
        String encodedPassword = passwordEncoder.encode(password);
        updatedPerson.setPassword(encodedPassword);
        personRepository.save(updatedPerson);
    }

    private void verifyBeforePersist(Person person){
        if(personRepository.findByUsername(person.getUsername()).isPresent())
            throw new PersonNotCreatedException("User with such username already exists");
        if(personRepository.findByUsername(person.getEmail()).isPresent())
            throw new PersonNotCreatedException("User with such email already exists");
//        if(person.getPassword().length() < 8 || person.getPassword().length() > 30)
//            throw new PersonNotCreatedException("Password should be between 8 and 30 characters in length");
    }
}
