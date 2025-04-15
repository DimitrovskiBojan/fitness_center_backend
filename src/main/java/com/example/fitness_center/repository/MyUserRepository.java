package com.example.fitness_center.repository;

import com.example.fitness_center.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findMyUserByUsername(String username);

    boolean existsByUsername(String username);

    Optional<MyUser> findMyUserById(Long id);
}
