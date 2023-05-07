package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.ProfileDto;
import com.astamatii.endava.webchat.dto.helpers.ProfileDtoNotBlankNotNullFlags;
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

        //This object will rule which error field in edit_profile to show, if exist (because by default BindingResult holds everything)
        model.addAttribute("errorFlags", new ProfileDtoNotBlankNotNullFlags());

        return "profile/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") @Valid ProfileDto profileDto,
                                BindingResult bindingResult, Model model) {

        // Profile flags are without errors. True - means fields were changed, False - fields will be ignored.
        ProfileDtoNotBlankNotNullFlags profileDtoNotBlankNotNullFlags = personService.profileDtoNotBlankNotNullFlags(profileDto);

        // Handling Validation. In order to update profile ignoring empty fields
        // Profile flags has errors added. True - means having errors, False - no errors.
        profileDtoNotBlankNotNullFlags.addErrors(bindingResult);

        model.addAttribute("errorFlags", profileDtoNotBlankNotNullFlags);

        if (profileDtoNotBlankNotNullFlags.findAnyError())
            return "profile/edit_profile";

        if (profileDtoNotBlankNotNullFlags.isAnyChangedCredentials()) {
            if (personService.passwordCheck(profileDto.getPasswordCheck())) {
                Person updatedUser = personService.prepareUpdatedUser(profileDto, personService.profileDtoNotBlankNotNullFlags(profileDto));
                personService.updateUser(updatedUser);
                return "redirect:/profile";
            } else {
                bindingResult.rejectValue("passwordCheck", "", "Password Check failed");
                return "profile/edit_profile";
            }
        }

        //When username, email or password are blank or unchanged, the update process will begin without password check.
        Person updatedUser = personService.prepareUpdatedUser(profileDto, personService.profileDtoNotBlankNotNullFlags(profileDto));
        personService.updateUser(updatedUser);
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
