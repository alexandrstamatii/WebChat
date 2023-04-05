package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
}
