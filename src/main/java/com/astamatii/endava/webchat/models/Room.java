package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;

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
    @JoinColumn(name = "created_by_user_id")
    private User user;

    @NotNull
    @UniqueElements(message = "This room name is already taken")
    @Size(min = 3, max = 30, message = "The room name must be between 3 and 30 letters in length")
    @Column(unique = true, length = 30)
    private String roomName;

    @ColumnDefault("false")
    private boolean onlyAllowedUsers;
    @ColumnDefault("false")
    private boolean passwordProtected;

    private String password;

    @ColumnDefault("now()")
    private ZonedDateTime createdAt;
    @ColumnDefault("now()")
    private ZonedDateTime updatedAt;

    @Min(0)
    private int messageCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "banned_users",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> bannedUsers;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "allowed_users",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> allowedUsers;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomUser> roomUser;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<PublicMessage> publicMessages;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<PrivateMessage> privateMessages;

    @PreUpdate
    public void setDefaultOnUpdate(){
        this.updatedAt = ZonedDateTime.now();
    }
}
