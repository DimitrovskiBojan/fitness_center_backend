package com.example.fitness_center.web;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.service.ClientService;
import com.example.fitness_center.service.MyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final MyUserService myUserService;

    public ClientController(ClientService clientService, MyUserService myUserService) {
        this.clientService = clientService;
        this.myUserService = myUserService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = this.clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id){
        MyUser myUser = this.myUserService.findById(id);
        Client client = this.clientService.findByUsername(myUser.getUsername());

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/credits")
    public ResponseEntity<Map<String, String>> addCredits(@RequestParam String username, @RequestParam Integer credits) {
        Map<String, String> response = new HashMap<>();
        try {
            // Call the service method to update credits
            this.clientService.updateCredits(username, credits);

            // If successful, return a success message
            response.put("message", "Credits Added");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            // If UsernameNotFoundException is thrown, return a BAD_REQUEST status with the error message
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



}
