package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
}
