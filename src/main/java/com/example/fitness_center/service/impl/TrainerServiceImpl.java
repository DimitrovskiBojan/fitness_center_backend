package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.Trainer;
import com.example.fitness_center.model.enums.Role;
import com.example.fitness_center.model.exceptions.TrainerNotFoundException;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.repository.TrainerRepository;
import com.example.fitness_center.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TrainerServiceImpl(TrainerRepository trainerRepository, MyUserRepository myUserRepository) {
        this.trainerRepository = trainerRepository;
        this.myUserRepository = myUserRepository;
    }

    @Override
    public Trainer save(String username, String password, Role role, Long rating, String name, String surname) {
        this.myUserRepository.save(new MyUser(username,passwordEncoder.encode(password),role));
        return this.trainerRepository.save(new Trainer(username,passwordEncoder.encode(password),role,rating,name,surname,""));
    }

    @Override
    public List<Trainer> findAll() {
        return this.trainerRepository.findAll();
    }

    @Override
    public Trainer findById(Long id) {
        return this.trainerRepository.findById(id).orElseThrow(() -> new TrainerNotFoundException(id));
    }

    @Override
    public Trainer findByUsername(String username) {
        return this.trainerRepository.findTrainerByUsername(username).orElseThrow(() -> new TrainerNotFoundException(1));
    }

    @Override
    public void saveImage(MultipartFile image, Long id) {
        try {
            String originalFilename = image.getOriginalFilename();
            Path path = Paths.get("C:\\Users\\Bojan\\AngularProjects\\fitness_center\\src\\assets\\images" + File.separator + originalFilename);
            MyUser user = this.myUserRepository.findMyUserById(id).orElseThrow(() -> new TrainerNotFoundException(id));
            System.out.println(user.getUsername());

            Trainer trainer = this.trainerRepository.findTrainerByUsername(user.getUsername()).orElseThrow(() -> new TrainerNotFoundException(user.getId()));

            trainer.setImage(originalFilename);
            this.trainerRepository.save(trainer);

            Files.write(path, image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
