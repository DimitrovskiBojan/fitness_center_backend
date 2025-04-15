package com.example.fitness_center.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MealPlanNotFoundException extends RuntimeException {

    public MealPlanNotFoundException(long id){
        super(String.format("DietPlan NOT FOUND"));
    }

}
