package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.security.PersonDetails;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.EmailExistsException;
import com.astamatii.endava.webchat.utils.exceptions.UsernameExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final PersonService personService;

    @GetMapping("/")
    public String redirectHomePage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("name", personService.findUserByUsername(username).getName());
        return "home/home";
    }

}
