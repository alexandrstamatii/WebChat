package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.EmailExistsException;
import com.astamatii.endava.webchat.utils.exceptions.UsernameExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final PersonService personService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") Person person) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid Person person, BindingResult bindingResult) {

        try {
            personService.findUserExistenceByEmail(person.getUsername());
        } catch (UsernameExistsException e) {
            bindingResult.rejectValue("username", "", e.getMessage());
        }

        try {
            personService.findUserExistenceByEmail(person.getEmail());
        } catch (EmailExistsException e) {
            bindingResult.rejectValue("email", "", e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        personService.registerUser(person);
        return "redirect:/login";
    }

//    @PostMapping("/register")
//    public String registerUsers(@RequestBody @Valid RegisterDto registerDto, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//
//            return "auth/register";
//        }
//        List<Object> userPresentObj = userService.isUserPresent(user);
//        if((Boolean) userPresentObj.get(0)){
//            model.addAttribute("successMessage", userPresentObj.get(1));
//            return "auth/register";
//        }
//
//        userService.saveUser(user);
//        model.addAttribute("successMessage", "User registered successfully!");
//
//        return "auth/login";
//    }
}