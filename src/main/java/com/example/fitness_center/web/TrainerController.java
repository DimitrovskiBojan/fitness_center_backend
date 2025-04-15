package com.example.fitness_center.web;

import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.Trainer;
import com.example.fitness_center.service.MyUserService;
import com.example.fitness_center.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final MyUserService myUserService;

    public TrainerController(TrainerService trainerService, MyUserService myUserService) {
        this.trainerService = trainerService;
        this.myUserService = myUserService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Trainer>> trainersPage(){

        List<Trainer> trainers = this.trainerService.findAll();

        return new ResponseEntity<>(trainers, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable Long id) {

        MyUser myUser = this.myUserService.findById(id);

        String username = myUser.getUsername();

        Trainer trainer = this.trainerService.findByUsername(username);

        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }


    @PostMapping("/image")
    public ResponseEntity<String> savePicture(@RequestParam("image") MultipartFile image, @RequestParam("id") String id) {
        try {
            System.out.println(Long.valueOf(id));
            this.trainerService.saveImage(image, Long.valueOf(id));
            return new ResponseEntity<>("Image Added", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
