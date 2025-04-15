package com.example.fitness_center.service;

import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.enums.Role;

public interface MyUserService {

    MyUser save (String username, String password, Role role);

    MyUser findByUsername(String username);

    boolean existsByUsername(String username);

    MyUser findById(Long id);
}
