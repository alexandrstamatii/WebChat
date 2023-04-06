package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.repositories.PersonRepository;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotCreatedException;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        updatedPerson.setLanguage(person.getLanguage());
        updatedPerson.setCity(person.getCity());
        updatedPerson.setTextColor(person.getTextColor());
        updatedPerson.setDob(person.getDob());
        updatedPerson.setName(person.getName());
        personRepository.save(person);
    }



    @Transactional
    public void register(Person person) {
        verifyBeforePersist(person);
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        personRepository.save(person);
    }

    private void verifyBeforePersist(Person person){
        if(personRepository.findByUsername(person.getUsername()).isPresent())
            throw new PersonNotCreatedException("User with such username already exists");
        if(personRepository.findByUsername(person.getEmail()).isPresent())
            throw new PersonNotCreatedException("User with such email already exists");
    }

    public void deletePerson(Person person) {
        findById(person.getId());
        personRepository.delete(person);
    }

//    @Transactional
//    public void updateProfileByValues(Long id, String name, LocalDate dob, Long languageId,
//                                      Long cityId, String textColor){
//        Person updatedPerson = findById(id);
//        personRepository.updateProfileByIdWithValues(id, name, dob, languageId, cityId, textColor);
//    }
//
//    @Transactional
//    public void updateProfileByPerson(Person person){
//        Person updatedPerson = findById(person.getId());
//        personRepository.updateProfileByIdWithPersonValues(person.getId(), person.getName(),person.getDob(),person.getLanguage(),
//                person.getCity(), person.getTextColor());
//    }
//
//    @Transactional
//    public void updateEmail(Long id, String email){
//        findById(id);
//        personRepository.updateEmailById(id, email);
//    }
//
//    public void updateUsername(Long id, String username){
//        findById(id);
//        personRepository.updateUsernameById(id, username);
//    }
//
//    @Transactional
//    public void updatePassword(Long id, String password) {
//        findById(id);
//        String encodedPassword = passwordEncoder.encode(password);
//
//        personRepository.updatePasswordById(id, encodedPassword);
//    }
}
