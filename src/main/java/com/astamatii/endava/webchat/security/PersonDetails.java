package com.astamatii.endava.webchat.security;

import com.astamatii.endava.webchat.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class PersonDetails implements UserDetails {
    private final Person user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return user
                .getLastLoggedInAt()
                .plusDays(user.getNonExpiredPeriodDays())
                .isAfter(ZonedDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {

        return user
                .getLockExpiresAt()
                .isBefore(ZonedDateTime.now());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
