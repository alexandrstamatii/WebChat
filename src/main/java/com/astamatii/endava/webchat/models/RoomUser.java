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
    private Person person;

    @ColumnDefault("false")
    private boolean muted;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(15)")
    private Role chatRole;

    @ColumnDefault("now()")
    private ZonedDateTime joinedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ignored_users")
    private Set<RoomUser> ignoredRoomUsers = new HashSet<>();
}
