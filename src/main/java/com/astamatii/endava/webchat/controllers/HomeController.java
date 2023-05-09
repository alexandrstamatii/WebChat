package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.security.PersonDetailsService;
import com.astamatii.endava.webchat.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final PersonDetailsService personDetailsService;
    private final PersonService personService;

    @GetMapping("/")
    public String redirectHomePage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        Person currentUser = personService.findUserByUsername(personDetailsService.getCurrentUsername());
        model.addAttribute("name", currentUser.getName());
        return "home/home";
    }

}
