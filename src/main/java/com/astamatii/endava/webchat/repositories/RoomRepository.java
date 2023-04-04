package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
