package com.example.fitness_center.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/trainer")
    @ResponseBody
    public ResponseEntity<Map<String, String>> welcomeTrainer() {
        // Retrieve the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract username from authentication object
        String username = authentication.getName();


        // Prepare the response data
        Map<String, String> response = new HashMap<>();
        response.put("username", username);


        // Return the response entity with OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/client")
    public String welcomeClient() {
        return "home-client";
    }
}
