package com.example.fitness_center.service;


import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.enums.Role;

import java.util.List;

public interface ClientService {

    Client save(String username, String password, Role role, Integer credits, String name, String surname);

    Client updateCredits(String username, Integer credits);

    List<Client> getAllClients();

    Client getClientById(Long id);

    Client findByUsername(String username);
}
