package com.example.fitness_center.auth;

import com.example.fitness_center.config.JwtService;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.enums.Role;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.service.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MyUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final MyUserService myUserService;

    private final JwtService jwtService;

    private  final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = MyUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CLIENT)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = repository.findMyUserByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(String.valueOf(this.myUserService.findByUsername(request.getUsername()).getRole()))
                .username(String.valueOf(this.myUserService.findByUsername(request.getUsername()).getUsername()))
                .id(String.valueOf(this.myUserService.findByUsername(request.getUsername()).getId()))
                .build();
    }
}
