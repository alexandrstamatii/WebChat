package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @NotBlank(message = "The name should not be blank or empty")
    @Size(min = 3, max = 30, message = "The name must be between 3 and 30 letters in length")
    @NotNull
    @Column(length = 30)
    private String name;

    @NotBlank(message = "The username should not be blank or empty")
    @Size(min = 3, max = 15, message = "The username must be between 3 and 15 letters in length")
    @NotNull
    @Column(unique = true, length = 15)
    private String username;

    @NotBlank(message = "The name should not be blank (with whitespaces at both ends) or empty")
    @Size(max = 30, message = "The email must have less or 30 letters in length")
    @Email(message = "The email should look like this: your_email@email.com")
    @NotNull
    @Column(unique = true, length = 30)
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.")
    @NotNull
    private String password;

    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    @NotBlank(message = "The name should not be blank (with whitespaces at both ends) or empty")
    @Column(columnDefinition = "varchar(7) default '#000000'")
    private String textColor;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'COLORED'")
    private Theme theme;

    @ManyToOne
    private City city;

    @ManyToOne
    private Language language;

    @Column(columnDefinition = "int4 default 0")
    private int bannedCount;

    @Column(columnDefinition = "int4 default 0")
    private int messageCount;

    @NotNull
    @ColumnDefault("true")
    private boolean enabled = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(15) default 'ROLE_USER'")
    private Role role;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime updatedAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime lastLoggedInAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime lockExpiresAt;

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
        this.name = name.trim();
        this.username = username.trim();
        this.email = email.trim();
        this.password = password;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor.trim();
    }

    @PrePersist
    public void setDefaultOnPersist() {
//        if (this.language == null) {
//            this.language = new Language();
//            this.language.setId(1L);
//        }

        ZonedDateTime currentTime = ZonedDateTime.now();

        this.enabled = true;
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
        this.lastLoggedInAt = currentTime;
        this.lockExpiresAt = currentTime;
        this.role = Role.ROLE_USER;
    }

    @PreUpdate
    public void setDefaultOnUpdate() {

    }
}
