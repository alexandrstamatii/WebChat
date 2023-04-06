package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    @Column(length = 30)
    private String name;

    @NotNull
    @Size(min = 3, max = 15, message = "The username must be between 3 and 15 letters in length")
    @Column(unique = true, length = 15)
    private String username;

    @NotNull
    @Email(message = "The email should look like this: your_email@email.com")
    @Column(unique = true, length = 30)
    private String email;

    @NotNull
    private String password;

    //@DateTimeFormat(pattern = "dd.MM.yyyy") //throws DateTimeParseException ("Date format should be dd.MM.yyyy")
    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    @Column(columnDefinition = "varchar(7) default '#000000'")
    private String textColor;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'COLORED'")
    private Theme theme;

    @Column(columnDefinition = "int4 default 0")
    private int bannedCount;

    @Column(columnDefinition = "int4 default 0")
    private int messageCount;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime updatedAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime lastLoggedInAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime lockExpiresAt;

    @ColumnDefault("true")
    private boolean nonLocked = true;

    @ColumnDefault("true")
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(15) default 'ROLE_USER'")
    private Role role;

    @ManyToOne
    private City city;

    @ManyToOne
    private Language language;

    @ManyToMany(mappedBy = "bannedPeople", cascade = CascadeType.ALL)
    private List<Room> bannedRooms;
    @ManyToMany(mappedBy = "allowedPeople", cascade = CascadeType.ALL)
    private List<Room> allowedRooms;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Room room;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<RoomUser> roomUsers;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PublicMessage> publicMessages;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PrivateMessage> privateMessages;

    public Person(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    public void setDefaultOnPersist(){
//        if (this.language == null) {
//            this.language = new Language();
//            this.language.setId(1L);
//        }

        ZonedDateTime currentTime = ZonedDateTime.now();

        this.enabled = true;
        this.nonLocked = true;
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
        this.lastLoggedInAt = currentTime;
        this.lockExpiresAt = currentTime;
        this.role = Role.ROLE_USER;
    }

    @PreUpdate
    public void setDefaultOnUpdate() {
        if (this.textColor == null) {
            textColor = "#000000";
        }
        if (this.theme == null) {
            this.theme = Theme.COLORED;
        }
        this.updatedAt = ZonedDateTime.now();
    }
}
