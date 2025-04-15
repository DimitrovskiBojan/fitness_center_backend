package com.example.fitness_center.web;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.enums.Role;
import com.example.fitness_center.service.ClientService;
import com.example.fitness_center.service.MyUserService;
import com.example.fitness_center.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final TrainerService trainerService;
    private final ClientService clientService;

    private final MyUserService myUserService;

    public RegisterController(TrainerService trainerService, ClientService clientService, MyUserService myUserService) {
        this.trainerService = trainerService;
        this.clientService = clientService;
        this.myUserService = myUserService;
    }

//    @PostMapping("/trainer")
//    public ResponseEntity<String> createTrainer(@RequestParam String username,
//                                                @RequestParam String password,
//                                                @RequestParam String name,
//                                                @RequestParam String surname) {
//
//        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || image.isEmpty()) {
//            return ResponseEntity.badRequest().body("All fields are required");
//        }
//        try {
//            this.trainerService.save(username, password, Role.TRAINER, 0L, name, surname, image);
//            return new ResponseEntity<>("Trainer registered successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/client")
    public ResponseEntity<Map<String, String>> createClient(@RequestBody Client client) {
        String username = client.getUsername();
        String password = client.getPassword();
        String name     = client.getName();
        String surname  = client.getSurname();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "All fields are required"));
        }


        if (this.myUserService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Username already exists"));
        }

        try {
            this.clientService.save(username, password, Role.CLIENT, 0, name, surname);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Client registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/trainer")
    public ResponseEntity<Map<String, String>> createTrainer(@RequestBody Client trainer) {
        String username = trainer.getUsername();
        String password = trainer.getPassword();
        String name     = trainer.getName();
        String surname  = trainer.getSurname();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "All fields are required"));
        }


        if (this.myUserService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Username already exists"));
        }

        try {
            this.trainerService.save(username, password, Role.TRAINER, 0L, name, surname);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Trainer registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


}
