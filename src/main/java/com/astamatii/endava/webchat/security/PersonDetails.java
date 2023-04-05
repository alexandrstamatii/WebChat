package com.astamatii.endava.webchat.security;

import com.astamatii.endava.webchat.models.Person;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class PersonDetails implements UserDetails {
    private final Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(person.getRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        ZonedDateTime lastLoginDate = person.getLastLoggedInAt();
        long logoutPeriodMonths = 12L;

        return lastLoginDate.plusMonths(logoutPeriodMonths).isAfter(ZonedDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return person.isNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return person.isEnabled();
    }
}
