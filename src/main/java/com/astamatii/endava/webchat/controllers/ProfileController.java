package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping
    public String profilePage(Model model) {
        model.addAttribute("user", personService.getCurrentUser());
        return "profile/profile";
    }

    @GetMapping("/edit_profile")
    public String editProfilePage(Model model) {
        ProfileDto user = modelMapper.map(personService.getCurrentUser(), ProfileDto.class);
        model.addAttribute("user", user);

        return "profile/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") @Valid ProfileDto profileDto,
                                BindingResult bindingResult) {
        Person currentUser = personService.getCurrentUser();

        //Handling Validation. In order to update profile ignoring empty fields.
        if (!profileDto.getUsername().isBlank() && bindingResult.hasFieldErrors("username")
                    && !profileDto.getUsername().equals(currentUser.getUsername())
                || !profileDto.getEmail().isBlank() && bindingResult.hasFieldErrors("email")
                    && !profileDto.getEmail().equals(currentUser.getEmail())
                || !profileDto.getName().isBlank() && bindingResult.hasFieldErrors("name")
                || !profileDto.getPassword().isBlank() && bindingResult.hasFieldErrors("password")
                || !profileDto.getTextColor().isBlank() && bindingResult.hasFieldErrors("testColor"))
            return "profile/edit_profile";

        //Password check. This will work only when username, email or password were changed and aren`t blank fields.
        if (!profileDto.getUsername().isBlank() && !profileDto.getUsername().equals(currentUser.getUsername())
                || !profileDto.getEmail().isBlank() && !profileDto.getEmail().equals(currentUser.getEmail())
                || !profileDto.getPassword().isBlank())

            if (personService.passwordCheck(profileDto.getPasswordCheck())) {

                personService.updateUser(profileDto);
                return "redirect:/profile";

            } else {
                bindingResult.rejectValue("passwordCheck", "", "Password Check failed");
                return "profile/edit_profile";
            }

        //When username, email or password are blank or unchanged, the update process will begin without password check.
        personService.updateUser(profileDto);
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(@ModelAttribute("user") ProfileDto profileDto,
                                BindingResult bindingResult) {

        if (personService.passwordCheck(profileDto.getPasswordCheck())) {

            personService.deleteCurrentUser();
            return "redirect:/logout";
        }
        bindingResult.rejectValue("passwordCheck", "", "Password Check Failed");
        return "profile/edit_profile";
    }

}
