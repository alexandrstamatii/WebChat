package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.security.PersonDetails;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotCreatedException;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PersonService personService;

    @GetMapping("/")
    public String redirectHomePage(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = personService.findByUsername(username);
        model.addAttribute("personName", person.getName());
        return "home/home";
    }

    @GetMapping("/profile")
    public String profilePage(Model model){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = personService.findByUsername(username);
        model.addAttribute("person", person);
        return "home/profile";
    }

    @GetMapping("/edit_profile")
    public String editProfilePage(@ModelAttribute("person") Person person){
        return "home/edit_profile";
    }

    @PatchMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "home/edit_profile";
        }
        try {
            personService.updateProfile(person);
        } catch (PersonNotFoundException | PersonNotCreatedException e) {
            bindingResult.rejectValue("enabled", "",e.getMessage());
            return "home/edit_profile";
        }
        return "redirect:/profile";
    }

    @DeleteMapping("/edit_profile")
    public String deleteProfile(@ModelAttribute("person") Person person, BindingResult bindingResult){

        try {
            personService.deletePerson(person);
        } catch (PersonNotFoundException e) {
            bindingResult.rejectValue("enabled", "",e.getMessage());
            return "home/edit_profile";
        }
        return "redirect:/profile";
    }
}
