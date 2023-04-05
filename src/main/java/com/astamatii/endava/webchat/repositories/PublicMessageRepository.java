package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicMessageRepository extends JpaRepository<PublicMessage, Long> {
}
