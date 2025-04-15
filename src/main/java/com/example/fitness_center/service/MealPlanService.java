package com.example.fitness_center.service;

import com.example.fitness_center.model.MealPlan;
import com.example.fitness_center.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface MealPlanService {

    List<MealPlan> findAll();

    Optional<MealPlan> findById(Long id);

    MealPlan save(Long price, String type, Long created_by, String description, String data);

    MealPlan edit(Long id, Long price, String type, Long created_by, String description, String data);

    List<MealPlan> findAllByCreated_by(Long id);

    void deleteById(Long id);

    MealPlan addPlanToClient(Long planId, String clientUsername);

    boolean canBuy(String planId, String clientUsername);
}
