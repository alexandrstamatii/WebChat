package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.IgnoredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IgnoredUserRepository extends JpaRepository<IgnoredUser, Long> {
}
