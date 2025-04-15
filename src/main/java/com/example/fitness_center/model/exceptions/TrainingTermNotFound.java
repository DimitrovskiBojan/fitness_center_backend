package com.example.fitness_center.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainingTermNotFound extends RuntimeException{

    public TrainingTermNotFound(long id){
        super(String.format("Training term NOT FOUND"));

    }

}
