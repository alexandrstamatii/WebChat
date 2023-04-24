package com.astamatii.endava.webchat.security;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.repositories.PersonRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final PersonRepository personRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        Optional<Person> userOptional = personRepository.findByUsername(username);
        if(userOptional.isEmpty())
            throw new UsernameNotFoundException("User by this username not found");

        Long userId = userOptional.get().getId(); // retrieve user ID from the principal

        Cookie idCookie = new Cookie("user_id", String.valueOf(userId));
        idCookie.setMaxAge(3600);
        idCookie.setSecure(true);
        idCookie.setHttpOnly(true);
        response.addCookie(idCookie);

        response.sendRedirect("/home");
    }
}
