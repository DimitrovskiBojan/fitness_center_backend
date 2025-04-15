package com.example.fitness_center.service;

import com.example.fitness_center.model.Trainer;
import com.example.fitness_center.model.TrainingTerm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrainingTermService {

    List<TrainingTerm> findAll();
    Optional<TrainingTerm> findById(Long id);

    TrainingTerm save(String startTime, String endTime, String price, String date, Long trainerId);
    Optional<TrainingTerm> edit(Long id, String startTime, String endTime, String price, String capacity, Long trainerId);

    void deleteById(Long id);


    List<TrainingTerm> findAllForTrainer(Long id);


    boolean canBuy(Long termId, String clientUsername);

    void addTermToClient(Long termId, String clientUsername);

    List<TrainingTerm> findAllForClient(Long id);

    List<TrainingTerm> findAllForTrainerProfile(Long id);

    void cancelTerm(Long id);
}
