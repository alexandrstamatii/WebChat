package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Person;
import com.astamatii.endava.webchat.models.Room;
import com.astamatii.endava.webchat.repositories.RoomRepository;
import com.astamatii.endava.webchat.utils.exceptions.NoChatRoomsException;
import com.astamatii.endava.webchat.utils.exceptions.RoomNotCreatedException;
import com.astamatii.endava.webchat.utils.exceptions.RoomNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findAll(){
        List<Room> list = roomRepository.findAll();
        if(list.isEmpty())
            throw new NoChatRoomsException("No rooms created yet");
        return list;
    }

    @Transactional
    public Room findByRoomName(String roomName){
        Optional<Room> roomOptional = roomRepository.findByRoomName(roomName);

        return roomOptional.orElseThrow(() -> new RoomNotFoundException("Person with this username not found"));
    }

    @Transactional
    public void createRoom(Room room){
        verifyBeforePersist(room);

        roomRepository.save(room);
    }

    private void verifyBeforePersist(Room room){
        if(roomRepository.findByRoomName(room.getRoomName()).isPresent())
            throw new RoomNotCreatedException("Room with such name already exists");
    }

}
