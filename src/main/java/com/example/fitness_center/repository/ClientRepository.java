package com.example.fitness_center.repository;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findMyUserByUsername(String username);

}
