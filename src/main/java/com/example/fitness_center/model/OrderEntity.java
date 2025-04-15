package com.example.fitness_center.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
    public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String number;

    @ManyToOne
    private Product product;

    public OrderEntity(String address, String number, Product product) {
        this.address = address;
        this.number = number;
        this.product = product;
    }

    public OrderEntity(Long id, String address, String number, Product product) {
        this.id = id;
        this.address = address;
        this.number = number;
        this.product = product;
    }

    public OrderEntity() {

    }
}

