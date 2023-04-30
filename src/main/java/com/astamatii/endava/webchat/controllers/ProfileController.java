package com.astamatii.endava.webchat.controllers;

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
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final PersonService personService;
//    private final ModelMapper modelMapper;

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

    public class PasswordCheck {
        private String value;

        public PasswordCheck() {
            value = "";
        }

        public String getValue() { return value; }

        public void setValue(String value) {
            this.value = value;
        }
    };

    @GetMapping("/edit_profile")
    public String editProfilePage(Model model) {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("user", personService.findUserByUsername(username));
        model.addAttribute("password-check", new PasswordCheck());

        return "profile/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") @Valid Person updatedUser,
                                @ModelAttribute("password-check") String enteredPassword,
                                BindingResult bindingResult) {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person currentUser = personService.findUserByUsername(username);

        System.out.println("HHHHHHHEEEEEEEEEERRRRRRRREEEEEEEEEEEE!!!!!!!!!!!!!!>>>>>>>>>>>>>>>>>  " + enteredPassword);

        //Handling Validation. In order to make possible profile updating with ignoring empty fields.
        if (personService.checkIfNotBlankOrEmpty(updatedUser.getName()))
            if (bindingResult.hasFieldErrors("name")) return "profile/edit_profile";
            else if (personService.checkIfNotBlankOrEmpty(updatedUser.getUsername())) {
                try {
                    if (!updatedUser.getUsername().equals(currentUser.getUsername()))
                        personService.findUserExistenceByUsername(updatedUser.getUsername());
                } catch (UsernameExistsException e) {
                    bindingResult.rejectValue("username", "", e.getMessage());
                }

                if (bindingResult.hasFieldErrors("username")) return "profile/edit_profile";
            } else if (personService.checkIfNotBlankOrEmpty(updatedUser.getEmail())) {
                try {
                    if (!updatedUser.getEmail().equals(currentUser.getEmail()))
                        personService.findUserExistenceByEmail(updatedUser.getEmail());
                } catch (EmailExistsException e) {
                    bindingResult.rejectValue("email", "", e.getMessage());
                }

                if (bindingResult.hasFieldErrors("email")) return "profile/edit_profile";
            } else if (personService.checkIfNotBlankOrEmpty(updatedUser.getPassword()))
                if (bindingResult.hasFieldErrors("password")) return "profile/edit_profile";

        if (true/*personService.passwordCheck(enteredPassword, username)*/) {

            personService.updateUser(updatedUser, username);
            return "redirect:/profile";
        }
//        bindingResult.rejectValue("password-check", "", "You`ve entered a wrong password");

        return "profile/edit_profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(/*@ModelAttribute("password_check") String enteredPassword,
                                BindingResult bindingResult*/) {

        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        if (true/*personService.passwordCheck(enteredPassword, username)*/) {

            personService.deleteUser(username);
            return "redirect:/logout";
        }

//        bindingResult.rejectValue("password-check", "", "You`ve entered a wrong password");

        return "profile/edit_profile";
    }

}
