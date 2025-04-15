package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.enums.Role;
import com.example.fitness_center.model.exceptions.TrainerNotFoundException;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserServiceImpl implements MyUserService {

    private final MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MyUserServiceImpl(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public MyUser save(String username, String password, Role role) {
        return myUserRepository.save(new MyUser(username,passwordEncoder.encode(password),role));
    }

    @Override
    public MyUser findByUsername(String username) {
        return this.myUserRepository.findMyUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.myUserRepository.existsByUsername(username);
    }

    @Override
    public MyUser findById(Long id) {
        return this.myUserRepository.findMyUserById(id).orElseThrow(() -> new TrainerNotFoundException(id));
    }


}
