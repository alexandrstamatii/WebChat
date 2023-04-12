package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.security.PersonDetails;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.exceptions.PersonEmailExistsException;
import com.astamatii.endava.webchat.utils.exceptions.PersonNotFoundException;
import com.astamatii.endava.webchat.utils.exceptions.PersonUsernameExistsException;
import com.astamatii.endava.webchat.utils.exceptions.PersonUsernameNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @ModelAttribute("profile_dto")
    public ProfileDto profileDto() throws PersonUsernameNotFoundException {
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        ProfileDto profileDto = modelMapper.map(personService.findByUsername(username), ProfileDto.class);
        return profileDto;
    }

    @GetMapping("/")
    public String redirectHomePage(){
        return "redirect:/home";
    }

//    @GetMapping("/home")
//    public String homePage(Model model){
//        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        model.addAttribute("personName", personService.findByUsername(username).getName());
//        return "home/home";
//    }

//    @GetMapping("/profile")
//    public String profilePage(Model model){
//        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        model.addAttribute("person", personService.findByUsername(username));
//        return "home/profile";
//    }

    @GetMapping("/home")
    public String homePage(){
        return "home/home";
    }

    @GetMapping("/profile")
    public String profilePage(){
        return "home/profile";
    }

    @GetMapping("/edit_profile")
    public String editProfilePage(Model model){
        model.addAttribute("current_password", "");

        return "home/edit_profile";
    }

    @PatchMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("profile_dto") @Valid ProfileDto profileDto,
                                @ModelAttribute("current_password") String currentPassword, BindingResult bindingResult) throws PersonUsernameNotFoundException {
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = personService.findByUsername(username);

        System.out.println("ATTENTION HERE 1 --->>>" + person);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(profileDto, person);

        System.out.println("ATTENTION HERE 2 --->>>" + person);

        System.out.println("ATTENTION HERE 3 --->>>" + profileDto);

        if (bindingResult.hasErrors()) {
            return "auth/edit_profile";
        }

//        try{
//            personService.confirmByPassword(currentPassword, username);
//        } catch (PersonUsernameNotFoundException e) {
//            bindingResult.rejectValue("current_password", "",e.getMessage());
//            return "auth/register";
//        }

        if(personService.verifyUsernameExistence(person)) {
            bindingResult.rejectValue("username", "", "This username already exists");
            return "auth/edit_profile";
        }
        if(personService.verifyEmailExistence(person)) {
            bindingResult.rejectValue("email", "", "This email already exists");
            return "auth/edit_profile";
        }

        personService.updatePerson(person);
        return "redirect:/edit_profile";
    }

    @DeleteMapping("/edit_profile")
    public String deleteProfile(){
        String username = ((PersonDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        personService.deletePerson(username);
        return "redirect:/profile";
    }
}
