package com.example.fitness_center.model;

import com.example.fitness_center.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    private Integer credits;
    private String name;
    private String surname;

    public Client(Long id, String username, Role role, String password, Integer credits, String name, String surname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.credits = credits;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public Client(String username, String password, Role role, Integer credits, String name, String surname) {
        this.username = username;
        this.password = password;
        this.credits = credits;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public Client() {
    }
}
