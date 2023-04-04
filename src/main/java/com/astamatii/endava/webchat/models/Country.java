package com.astamatii.endava.webchat.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Icon icon;
    private String countryName;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private City city;

}
