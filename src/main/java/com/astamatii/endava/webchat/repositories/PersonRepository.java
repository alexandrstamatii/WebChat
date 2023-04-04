package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
