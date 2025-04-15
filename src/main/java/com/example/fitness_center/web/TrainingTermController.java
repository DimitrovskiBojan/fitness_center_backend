package com.example.fitness_center.web;

import com.example.fitness_center.model.TrainingTerm;
import com.example.fitness_center.service.TrainingTermService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainingTerms")
public class TrainingTermController {

    private final TrainingTermService trainingTermService;

    public TrainingTermController(TrainingTermService trainingTermService) {
        this.trainingTermService = trainingTermService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingTerm>> getAll(){
        List<TrainingTerm> trainingTerms = this.trainingTermService.findAll();

        return new ResponseEntity<>(trainingTerms, HttpStatus.OK);
    }

    @GetMapping("/forTrainer/{id}")
    public ResponseEntity<List<TrainingTerm>> getForTrainer(@PathVariable Long id) {
        System.out.println(id);
        List<TrainingTerm> trainingTerms = this.trainingTermService.findAllForTrainer(id);

        return new ResponseEntity<>(trainingTerms, HttpStatus.OK);
    }
    @GetMapping("/forTrainerAll/{id}")
    public ResponseEntity<List<TrainingTerm>> getForTrainerAll(@PathVariable Long id) {
        System.out.println(id);
        List<TrainingTerm> trainingTerms = this.trainingTermService.findAllForTrainerProfile(id);

        return new ResponseEntity<>(trainingTerms, HttpStatus.OK);
    }
    @GetMapping("/forClient/{id}")
    public ResponseEntity<List<TrainingTerm>> getForClient(@PathVariable Long id) {
        System.out.println(id);
        List<TrainingTerm> trainingTerms = this.trainingTermService.findAllForClient(id);

        return new ResponseEntity<>(trainingTerms, HttpStatus.OK);
    }

    @PostMapping("/addNew")
    public ResponseEntity<TrainingTerm> createNewTerm(@RequestParam Long trainerId, @RequestParam String price, @RequestParam String date, @RequestParam String startTime, @RequestParam String endTime){

        TrainingTerm trainingTerm = this.trainingTermService.save(startTime,endTime,price,date,trainerId);

        return new ResponseEntity<>(trainingTerm, HttpStatus.CREATED);
    }

    @GetMapping("/cancelTerm/{id}")
    public ResponseEntity<Map<String,String>> cancelTerm(@PathVariable Long id){
        Map<String , String> response = new HashMap<>();

        try{
            this.trainingTermService.cancelTerm(id);
            response.put("message", "Term canceled");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", "Invalid term ID");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<Map<String,String>> reserveTerm(@RequestParam String termId, @RequestParam String clientUsername){
        Map<String, String> response = new HashMap<>();


        try{
            if(!this.trainingTermService.canBuy(Long.parseLong(termId),clientUsername)){
                response.put("message", "No enough credits");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            this.trainingTermService.addTermToClient(Long.parseLong(termId),clientUsername);
            response.put("message", "Term reserved");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NumberFormatException e) {
            response.put("error", "Invalid term ID");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTerm(@PathVariable Long id){
        try{
            this.trainingTermService.deleteById(id);
            return new ResponseEntity<>("Term Deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
