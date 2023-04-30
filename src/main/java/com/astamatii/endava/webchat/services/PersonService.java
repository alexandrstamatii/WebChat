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
        return personRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
    }

//    @Transactional
//    public Person findUserByEmail(String email) {
//        return personRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
//    }
//
    @Transactional
    public Person findUserById(Long id) {
        return personRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    @Transactional
    public void findUserExistenceByUsername(String username){
        if (personRepository.findByUsername(username).isPresent()) throw new UsernameExistsException();
    }

    @Transactional
    public void findUserExistenceByEmail(String email){
        if (personRepository.findByEmail(email).isPresent()) throw new EmailExistsException();
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
    public void updateUser(Person updatedUser, String username){
        Person currentUser = findUserByUsername(username);

        //Blank and Empty String and null fields, will be replaced by current values
        if (!updatedUser.getPassword().isBlank()) currentUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        if (!updatedUser.getUsername().isBlank()) findUserExistenceByUsername(updatedUser.getUsername());

        if (!updatedUser.getEmail().isBlank()) findUserExistenceByEmail(updatedUser.getEmail());

        if (!updatedUser.getName().isBlank()) currentUser.setName(updatedUser.getName());
        if (!updatedUser.getTextColor().isBlank()) currentUser.setTextColor(updatedUser.getTextColor());
        if (updatedUser.getDob() != null) currentUser.setDob(updatedUser.getDob());
        if (updatedUser.getTheme() != null) currentUser.setTheme(updatedUser.getTheme());
        if (updatedUser.getCity() != null) currentUser.setCity(updatedUser.getCity());
        if (updatedUser.getLanguage() != null) currentUser.setLanguage(updatedUser.getLanguage());

        currentUser.setUpdatedAt(ZonedDateTime.now());
        personRepository.save(currentUser);
    }

    @Transactional
    public boolean passwordCheck(String enteredPassword, String username) {
        String encodedEnteredPassword =  passwordEncoder.encode(enteredPassword);
        return findUserByUsername(username).getPassword().equals(encodedEnteredPassword);
    }

    @Transactional
    public void deleteUser(String username) {
        personRepository.delete(findUserByUsername(username));
    }

//    public Person mapProfileToPerson(ProfileDto profileDto) {
//        Method[] methods = profileDto.getClass().getMethods();
//
//        Arrays.stream(methods)
//                .filter(method -> method.getName().startsWith("get"))
//                .forEach(getterMethod -> {
//
//                    try {
//
//                        Object result = getterMethod.invoke(profileDto);
//                        if (result instanceof String) {
//                            if (((String) result).isEmpty()){
//                                Method setterMethod =  profileDto.getClass().getMethod("set" + getterMethod.getName().substring(3).toLowerCase());
//                                setterMethod.invoke(profileDto, (String) null);
//                            }
//
//                        }
//
//                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//    }

}
