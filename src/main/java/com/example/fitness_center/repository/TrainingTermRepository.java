package com.example.fitness_center.repository;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.TrainingTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingTermRepository extends JpaRepository<TrainingTerm, Long> {

    List<TrainingTerm> findAllByTrainerIdAndIsReservedFalse(Long id);

    List<TrainingTerm> findAllByReservedBy(Client client);

    List<TrainingTerm> findAllByTrainerId(Long id);
}
