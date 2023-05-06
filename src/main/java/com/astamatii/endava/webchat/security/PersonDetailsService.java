package com.astamatii.endava.webchat.security;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.repositories.PersonRepository;
import com.astamatii.endava.webchat.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        Optional<Person> userOptional = personRepository.findByUsername(usernameOrEmail);

        if (userOptional.isEmpty())
            userOptional = personRepository.findByEmail(usernameOrEmail);
        if (userOptional.isEmpty())
            throw new ResourceNotFoundException("User by this username or email not found");

        Person user = userOptional.get();
        user.setLastLoggedInAt(ZonedDateTime.now());
        personRepository.save(user);

        return new PersonDetails(user);
    }

    public String getCurrentUsername() {
        return ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public void updateUserDetails(Person user) {
        UserDetails updatedUserDetails = new PersonDetails(user);
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
    }
}
