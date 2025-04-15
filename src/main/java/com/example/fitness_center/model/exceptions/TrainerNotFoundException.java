package com.example.fitness_center.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainerNotFoundException extends RuntimeException{

    public TrainerNotFoundException(long id){
        super(String.format("Trainer NOT FOUND"));

    }

}

