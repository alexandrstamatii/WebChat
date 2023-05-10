package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.security.PersonDetailsService;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.utils.mappers.PersonMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final PersonDetailsService personDetailsService;
    private final PersonMapper personMapper;

    @GetMapping
    public String profilePage(Model model) {
        Person currentUser = personService.findUserByUsername(personDetailsService.getCurrentUsername());
        model.addAttribute("user", currentUser);

        return "profile/profile";
    }

    @GetMapping("/edit_profile")
    public String editProfilePage(Model model) {
        Person currentUser = personService.findUserByUsername(personDetailsService.getCurrentUsername());
        ProfileDto profileDto = personMapper.mapToProfileDto(currentUser);
        model.addAttribute("user", profileDto);

        return "profile/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") @Valid ProfileDto profileDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "profile/edit_profile";

        Person currentUser = personService.findUserByUsername(personDetailsService.getCurrentUsername());

        String email = profileDto.getEmail(),
                username = profileDto.getUsername();

        if ((username.isBlank() || username.equals(currentUser.getUsername())
                && (email.isBlank() || email.equals(currentUser.getEmail()))
                && profileDto.getPassword().isBlank())) {

            //When username, email or password are blank or unchanged, the update process will begin without password check.
            personMapper.updatePerson(currentUser, profileDto);
            personService.updateUser(currentUser);

            return "redirect:/profile";
        }

        //When username, email or password were changed, the update process will start after password verification.
        if (personService.passwordCheck(profileDto.getPasswordCheck(), currentUser.getPassword())) {
            personMapper.updatePerson(currentUser, profileDto);
            personDetailsService.updateUserDetails(personService.updateUser(currentUser));

            return "redirect:/profile";
        }

        bindingResult.rejectValue("passwordCheck", "", "Password Check failed");
        return "profile/edit_profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(@ModelAttribute("user") ProfileDto profileDto,
                                BindingResult bindingResult) {
        Person currentUser = personService.findUserByUsername(personDetailsService.getCurrentUsername());

        if (personService.passwordCheck(profileDto.getPasswordCheck(), currentUser.getPassword())) {

            personService.deleteUser(currentUser);
            return "redirect:/logout";
        }
        bindingResult.rejectValue("passwordCheck", "", "Password Check Failed");
        return "profile/edit_profile";
    }

}
