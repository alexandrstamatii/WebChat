package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class PublicMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Room room;
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User user;

    private String messageText;

    @ColumnDefault("now()")
    private ZonedDateTime writtenAt;
}
