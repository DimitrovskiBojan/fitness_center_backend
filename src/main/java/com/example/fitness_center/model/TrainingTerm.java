package com.example.fitness_center.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class TrainingTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startTime;
    private String endTime;
    private String price;
    private String date;
    private Boolean isReserved;
    @ManyToOne
    private Client reservedBy;
    @ManyToOne
    private Trainer trainer;

    public TrainingTerm(Long id, String startTime, String endTime, String price, String capacity, Trainer trainer) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.date = date;
        this.trainer = trainer;
        this.isReserved = false;
        this.reservedBy = null;
    }

    public TrainingTerm(String  startTime, String endTime, String price, String date, Trainer trainer) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.date = date;
        this.trainer = trainer;
        this.isReserved = false;
        this.reservedBy = null;
    }

    public TrainingTerm() {
    }
}
