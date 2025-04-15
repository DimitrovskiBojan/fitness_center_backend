package com.example.fitness_center.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;
    private String weight;
    private String taste;
    private String type;
    private String manufacturer;
    private String image;
    private Boolean active = true;


    public Product(Long id, String name, Long price, String weight, String taste, String type, String manufacturer, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.taste = taste;
        this.type = type;
        this.manufacturer = manufacturer;
        this.image = image;
        this.active = true;
    }

    public Product(String name, Long price, String weight, String taste, String type, String manufacturer, String image) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.taste = taste;
        this.type = type;
        this.manufacturer = manufacturer;
        this.image = image;
        this.active = true;
    }

    public Product() {

    }
}
