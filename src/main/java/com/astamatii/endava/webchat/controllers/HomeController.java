package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.security.PersonDetails;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.PersonEmailExistsException;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotCreatedException;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotFoundException;
import com.astamatii.endava.webchat.utils.exceptions.PersonUsernameExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String redirectHomePage(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("personName", personService.findByUsername(username).getName());
        return "home/home";
    }

    @GetMapping("/profile")
    public String profilePage(Model model){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("person", personService.findByUsername(username));
        return "home/profile";
    }

    @GetMapping("/edit_profile")
    public String editProfilePage(@ModelAttribute("profile_dto") ProfileDto profileDto){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        profileDto = modelMapper.map(personService.findByUsername(username), ProfileDto.class);

        return "home/edit_profile";
    }

    @PatchMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("profile_dto") @Valid ProfileDto profileDto, BindingResult bindingResult){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = personService.findByUsername(username);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(profileDto, person);

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
//        try {
//            personService.register(person);
//        } catch (PersonUsernameExistsException e) {
//            bindingResult.rejectValue("username", "",e.getMessage());
//            return "auth/register";
//        } catch (PersonEmailExistsException e) {
//            bindingResult.rejectValue("email", "",e.getMessage());
//            return "auth/register";
//        }

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
