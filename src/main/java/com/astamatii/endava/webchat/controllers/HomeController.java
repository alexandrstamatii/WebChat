package com.astamatii.endava.webchat.controllers;

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
    private final PersonService personService;

    @GetMapping("/")
    public String redirectHomePage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("name", personService.getCurrentUser().getName());
        return "home/home";
    }

}
