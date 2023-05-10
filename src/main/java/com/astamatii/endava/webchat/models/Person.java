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
    @Size(min = 3, max = 50, message = "The name length must be between 3 and 50 letters")
    @NotNull
    @Column(length = 50)
    private String name;

    @NotBlank(message = "The username should not be blank or empty")
    @Size(min = 3, max = 30, message = "The username length must be between 3 and 30 letters")
    @Pattern(regexp = "^[a-z][a-z0-9]*$", message = "The username must contain only lowercase letters and numbers, and start from a letter")
    @NotNull
    @Column(unique = true, length = 30)
    private String username;

    @NotBlank(message = "The name should not be blank (with whitespaces at both ends) or empty")
    @Size(max = 30, message = "The email can have maximum 30 characters")
    @Email(message = "The email should look like this: your_email@email.com")
    @NotNull
    @Column(unique = true, length = 30)
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain: at least 8 characters, one uppercase letter, one lowercase letter, one digit")
    @Size(max = 255, message = "The password can have maximum 255 characters")
    @NotNull
    private String password;

    private LocalDate dob;

    @NotBlank(message = "The name should not be blank (with whitespaces at both ends) or empty")
    @Column(columnDefinition = "varchar(7) DEFAULT '#000000'")
    private String textColor;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) DEFAULT 'COLORED'")
    private Theme theme;

    @ManyToOne
    private City city;

    @ManyToOne
    private Language language;

    @Column(columnDefinition = "int4 DEFAULT 0")
    private int bannedCount;

    @Column(columnDefinition = "int4 DEFAULT 0")
    private int messageCount;

    @Column(columnDefinition = "bool NOT NULL DEFAULT true")
    private boolean enabled;

    @Column(columnDefinition = "int2 NOT NULL DEFAULT 365")
    private short nonExpiredPeriodDays;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(15) NOT NULL DEFAULT 'ROLE_USER'")
    private Role role;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private ZonedDateTime updatedAt;

    @Column(columnDefinition = "timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)")
    private ZonedDateTime lastLoggedInAt;

    @Column(columnDefinition = "timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)")
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

    public void setTextColor(String textColor) {
        if (textColor.matches("^#[0-9A-Fa-f]{6}$"))
            this.textColor = textColor;
        else this.textColor = "#000000";
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
        this.nonExpiredPeriodDays = 365;
    }

    @PreUpdate
    public void setDefaultOnUpdate() {

    }
}
