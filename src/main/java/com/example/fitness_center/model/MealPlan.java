package com.example.fitness_center.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long price;
    private String type;
    private String description;
    @ManyToOne
    private Trainer created_by;
    @ManyToMany
    private List<Client> purchasedBy;
    private String data;

    public MealPlan(Long price, String type, Trainer created_by, String description, String data) {
        this.price = price;
        this.type = type;
        this.created_by = created_by;
        this.description = description;
        this.data = data;
    }

    public MealPlan() {
    }

    public MealPlan(Long id, Long price, String type, Trainer created_by, String description, String data) {
        this.id = id;
        this.price = price;
        this.type = type;
        this.created_by = created_by;
        this.description = description;
        this.data = data;
    }
}
