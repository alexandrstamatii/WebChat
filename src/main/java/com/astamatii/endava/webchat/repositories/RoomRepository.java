package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
