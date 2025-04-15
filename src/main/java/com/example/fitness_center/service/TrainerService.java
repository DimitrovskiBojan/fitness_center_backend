package com.example.fitness_center.service;

import com.example.fitness_center.model.Trainer;
import com.example.fitness_center.model.enums.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Trainer save(String username, String password, Role role, Long rating, String name, String surname);

    List<Trainer> findAll();

    Trainer findById(Long id);

    Trainer findByUsername(String username);


    void saveImage(MultipartFile image, Long id);
}
