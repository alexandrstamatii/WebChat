package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
