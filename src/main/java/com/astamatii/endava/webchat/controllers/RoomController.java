package com.astamatii.endava.webchat.controllers;

import com.astamatii.endava.webchat.dto.RoomInfoDto;
import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.models.Room;
import com.astamatii.endava.webchat.security.PersonDetails;
import com.astamatii.endava.webchat.services.PersonService;
import com.astamatii.endava.webchat.services.RoomService;
import com.astamatii.endava.webchat.utils.exceptions.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final PersonService personService;
    private final RoomService roomService;
    private final ModelMapper modelMapper;

    @GetMapping("/rooms")
    public String roomsPage(){
        return "room/rooms";
    }

    @ResponseBody
    @GetMapping("/get_rooms")
    public ResponseEntity<List<RoomInfoDto>> getRooms(){
        List<RoomInfoDto> response = null;
        try {
            response = roomService.findAll().stream().map(room -> modelMapper.map(room, RoomInfoDto.class)).toList();
        } catch (ChatRoomsNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/create_room")
    public String createRoomPage(Model model) throws UsernameNotFoundException {
        String username = ((PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = null;
        person = personService.findUserByUsername(username);
        model.addAttribute("room", new Room());
        model.addAttribute("personId", person.getId());
        return "room/create_room";
    }

    @PostMapping("/create_room")
    public String createRoom(@ModelAttribute("room") @Valid Room room, @ModelAttribute("personId") Long personId,  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "room/create_room";
        }
        try {
            roomService.createRoom(room);
        } catch (RoomNameExistsException e) {
            bindingResult.rejectValue("enabled", "",e.getMessage());
            return "room/create_room";
        }

        return "redirect:/rooms";
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException (ChatRoomsNotFoundException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), ZonedDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
