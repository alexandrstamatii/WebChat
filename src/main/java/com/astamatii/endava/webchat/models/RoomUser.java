package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class RoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Room room;
    @ManyToOne
    private User user;

    @ColumnDefault("false")
    private boolean muted;

    @Column(length = 10)
    private String role;

    @ColumnDefault("now()")
    private ZonedDateTime joinedAt;

    @OneToMany(mappedBy = "roomUser", cascade = CascadeType.ALL)
    private Set<IgnoredUser> ignoredUsers = new HashSet<>();
}
