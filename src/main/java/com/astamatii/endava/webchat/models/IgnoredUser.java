package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class IgnoredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomUser roomUser;

    @OneToMany(mappedBy = "ignoredUser", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<RoomUser> ignoredRoomUsers = new HashSet<>();

    // getters and setters

}
