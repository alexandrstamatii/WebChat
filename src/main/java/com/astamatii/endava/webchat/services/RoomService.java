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

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findAll() {
        List<Room> list = roomRepository.findAll();

        if (list.isEmpty())
            throw new ChatRoomsNotFoundException();

        return list;
    }

    @Transactional
    public Room findByRoomName(String roomName) {
        return roomRepository.findByRoomName(roomName).orElseThrow(() -> new RoomNameNotFoundException(roomName));
    }

    @Transactional
    public void createRoom(Room room) {
        findRoomExistenceByRoomName(room.getRoomName());

        roomRepository.save(room);
    }

    @Transactional
    public void findRoomExistenceByRoomName(String roomName) {
        if (roomRepository.findByRoomName(roomName).isPresent()) throw new RoomNameExistsException(roomName);
    }
}
