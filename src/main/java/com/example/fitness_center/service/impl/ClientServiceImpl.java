package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.enums.Role;
import com.example.fitness_center.model.exceptions.ClientNotFoundException;
import com.example.fitness_center.model.exceptions.TrainerNotFoundException;
import com.example.fitness_center.repository.ClientRepository;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, MyUserRepository myUserRepository) {
        this.clientRepository = clientRepository;
        this.myUserRepository = myUserRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return this.clientRepository.findAll();
    }



    @Override
    public Client getClientById(Long id) {
        return this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public Client findByUsername(String username) {
        return this.clientRepository.findMyUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public Client save(String username, String password, Role role, Integer credits, String name, String surname) {

        this.myUserRepository.save(new MyUser(username,passwordEncoder.encode(password),role));

        return this.clientRepository.save(new Client(username,passwordEncoder.encode(password),role,credits,name,surname));
    }

    @Override
    public Client updateCredits(String username, Integer credits) {
        // Find the client by username, throw UsernameNotFoundException if not found
        Client client = this.clientRepository.findMyUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

        // Update the client's credits if found
        int credits_pom = client.getCredits() + credits;
        client.setCredits(credits_pom);

        // Save the updated client object to the repository
        return this.clientRepository.save(client);
    }

}
