package com.example.fitness_center.auth;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

   private String id;
   private String token;
   private String role;
   private String username;
   private Client client;

}
