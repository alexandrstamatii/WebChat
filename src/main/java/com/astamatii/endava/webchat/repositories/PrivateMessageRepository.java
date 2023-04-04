package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
}
