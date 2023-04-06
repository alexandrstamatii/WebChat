package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotCreatedException;
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
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("person") Person person){
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "auth/register";
        }
        try {
            personService.register(person);
        } catch (PersonNotCreatedException e) {
            bindingResult.rejectValue("enabled", "",e.getMessage());
            return "auth/register";
        }

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