package com.example.fitness_center.repository;

import com.example.fitness_center.model.MealPlan;
import com.example.fitness_center.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

    @Query("SELECT dp FROM MealPlan dp WHERE dp.created_by = :trainer")
    List<MealPlan> findAllByCreated_by(@Param("trainer")Trainer trainer);

}
