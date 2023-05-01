package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.ProfileDto;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final PersonService personService;
    private final ModelMapper modelMapper;

//    @ModelAttribute("profile_dto")
//    public ProfileDto profileDto() throws UsernameNotFoundException {
//        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        ProfileDto profileDto = modelMapper.map(personService.findUserByUsername(username), ProfileDto.class);
//        return profileDto;
//    }

    @GetMapping
    public String profilePage(Model model) {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("user", personService.findUserByUsername(username));
        return "profile/profile";
    }

    @GetMapping("/edit_profile")
    public String editProfilePage(Model model) {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        ProfileDto user = modelMapper.map(personService.findUserByUsername(username), ProfileDto.class);
        model.addAttribute("user", user);

        return "profile/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") @Valid ProfileDto profileDto,
                                BindingResult bindingResult) {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person currentUser = personService.findUserByUsername(username);

        //Handling Validation. In order to update profile ignoring empty fields.
        if (!profileDto.getUsername().isBlank() && bindingResult.hasFieldErrors("username") && !profileDto.getUsername().equals(username) ||
                !profileDto.getEmail().isBlank() && bindingResult.hasFieldErrors("email") && !profileDto.getEmail().equals(currentUser.getEmail()) ||
                !profileDto.getName().isBlank() && bindingResult.hasFieldErrors("name") ||
                !profileDto.getPassword().isBlank() && bindingResult.hasFieldErrors("password") ||
                !profileDto.getTextColor().isBlank() && bindingResult.hasFieldErrors("testColor"))
            return "profile/edit_profile";

        if (personService.passwordCheck(profileDto.getPasswordCheck(), currentUser)) {

            personService.updateUser(profileDto, currentUser);
            return "redirect:/profile";
        }

        bindingResult.rejectValue("passwordCheck", "", "Password Check failed");

        return "profile/edit_profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(@ModelAttribute("user") ProfileDto profileDto,
                                BindingResult bindingResult) {

        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person currentUser = personService.findUserByUsername(username);

        if (personService.passwordCheck(profileDto.getPasswordCheck(), currentUser)) {

            personService.deleteUser(username);
            return "redirect:/logout";
        }
        bindingResult.rejectValue("passwordCheck", "", "Password Check Failed");
        return "profile/edit_profile";
    }

}
