package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
