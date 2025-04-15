package com.example.fitness_center.model;

import com.example.fitness_center.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Long rating;
    private String name;
    private String surname;
    private Integer credits;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    private String image;

    public Trainer(Long id, String username, Role role, String password, Long rating, String name, String surname, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.role = role;
        this.credits = 0;

    }

    public Trainer(String username, String password, Role role, Long rating, String name, String surname, String image) {
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.role = role;
        this.credits = 0;
    }

    public Trainer(){}
}
