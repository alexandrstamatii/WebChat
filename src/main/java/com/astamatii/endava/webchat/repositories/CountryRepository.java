package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
