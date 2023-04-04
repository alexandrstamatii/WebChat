package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City city;

    @ManyToOne
    private Language language;

    @NotNull
    @UniqueElements(message = "This username already exists")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 letters in length")
    @Column(unique = true, length = 20)
    private String username;

    @NotNull
    @UniqueElements(message = "This email already exists")
    @Email(message = "The email should look like this: your_email@email.com")
    @Column(unique = true, length = 30)
    private String email;

    @NotNull
    private String password;

    //@DateTimeFormat(pattern = "dd.MM.yyyy") //throws DateTimeParseException ("Date format should be dd.MM.yyyy")
    private LocalDate dob;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Hex value must be in the format #RRGGBB")
    @ColumnDefault("#000000")
    @Column(length = 7)
    private String textColor;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("COLORED")
    @Column(length = 10)
    private Theme theme;

    @Min(0)
    private int bannedCount;
    @Min(0)
    private int messageCount;

    @ColumnDefault("now()")
    private ZonedDateTime createdAt;
    @ColumnDefault("now()")
    private ZonedDateTime updatedAt;

    @ManyToMany(mappedBy = "bannedUsers", cascade = CascadeType.ALL)
    private List<Room> bannedRooms;
    @ManyToMany(mappedBy = "allowedUsers", cascade = CascadeType.ALL)
    private List<Room> allowedRooms;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Room room;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoomUser> roomUsers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PublicMessage> publicMessages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PrivateMessage> privateMessages;

    @PrePersist
    public void setDefaultOnPersist(){
        if (this.language == null) {
            this.language = new Language();
            this.language.setId(1L);
        }
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
