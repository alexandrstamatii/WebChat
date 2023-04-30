package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Person person;

    @NotNull
    @Size(min = 3, max = 15, message = "The room name must be between 3 and 15 letters in length")
    @Column(unique = true, length = 15)
    private String roomName;

    @Size(max = 60, message = "The room description must be less than 60 letters long")
    @Column(length = 60)
    private String roomDescription;

    @ColumnDefault("false")
    private boolean onlyAllowedUsers;
    @ColumnDefault("false")
    private boolean passwordProtected;

    private String password;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime createdAt;
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime updatedAt;

    @Min(0)
    private int messageCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "banned_people",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> bannedPeople;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "allowed_people",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> allowedPeople;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomUser> roomUser;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<PublicMessage> publicMessages;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<PrivateMessage> privateMessages;

    public Room(Person person) {
        this.person = person;
    }

    @PreUpdate
    public void setDefaultOnUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}
