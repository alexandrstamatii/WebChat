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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String editProfilePage(Model model)  {
        ProfileDto user = modelMapper.map(personService.getCurrentUser(), ProfileDto.class);
        model.addAttribute("user", user);

        //This list will rule which error in edit_profile to show, which to hide in the view (because by default BindingResult holds everything)
        List<Boolean> errorFlags = Arrays.asList(false, false, false, false, false, false);
        model.addAttribute("errorFlags", errorFlags);

        return "profile/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") @Valid ProfileDto profileDto,
                                BindingResult bindingResult, Model model) {
        Person currentUser = personService.getCurrentUser();

        List<Boolean> errorFlags = new ArrayList<>(6);

        //Handling Validation. In order to update profile ignoring empty fields.
        //errorFlags must add strictly in the order shown bellow:
        errorFlags.add(!profileDto.getName().isBlank() && bindingResult.hasFieldErrors("name"));
        errorFlags.add(!profileDto.getUsername().isBlank() && bindingResult.hasFieldErrors("username")
                && !profileDto.getUsername().equals(currentUser.getUsername()));
        errorFlags.add(!profileDto.getEmail().isBlank() && bindingResult.hasFieldErrors("email")
                && !profileDto.getEmail().equals(currentUser.getEmail()));
        errorFlags.add(!profileDto.getPassword().isBlank() && bindingResult.hasFieldErrors("password"));
        errorFlags.add(!bindingResult.hasFieldErrors("dob"));
        errorFlags.add(!profileDto.getTextColor().isBlank() && bindingResult.hasFieldErrors("testColor"));

        model.addAttribute("errorFlags", errorFlags);

        if (errorFlags.stream().anyMatch(flag -> flag))
            return "profile/edit_profile";

        if (bindingResult.hasErrors())
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
