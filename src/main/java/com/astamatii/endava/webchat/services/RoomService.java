package com.astamatii.endava.webchat.services;

import com.astamatii.endava.webchat.models.Room;
import com.astamatii.endava.webchat.repositories.RoomRepository;
import com.astamatii.endava.webchat.utils.exceptions.ChatRoomsNotFoundException;
import com.astamatii.endava.webchat.utils.exceptions.RoomNameExistsException;
import com.astamatii.endava.webchat.utils.exceptions.RoomNameNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findAll() throws ChatRoomsNotFoundException {
        List<Room> list = roomRepository.findAll();
        if(list.isEmpty())
            throw new ChatRoomsNotFoundException();
        return list;
    }

    @Transactional
    public Room findByRoomName(String roomName) throws RoomNameNotFoundException {
        Optional<Room> roomOptional = roomRepository.findByRoomName(roomName);

        return roomOptional.orElseThrow(RoomNameNotFoundException::new);
    }

    @Transactional
    public void createRoom(Room room) throws RoomNameExistsException {
        if(verifyRoomNameExistence(room))
            throw new RoomNameExistsException();

        roomRepository.save(room);
    }

    private boolean verifyRoomNameExistence(Room room){
        return roomRepository.findByRoomName(room.getRoomName()).isPresent();
    }

}
