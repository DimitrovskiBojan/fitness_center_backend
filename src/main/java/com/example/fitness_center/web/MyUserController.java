package com.example.fitness_center.web;

import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyUserController {

    private final MyUserService myUserService;


    public MyUserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }
}
