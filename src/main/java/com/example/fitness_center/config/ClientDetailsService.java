package com.example.fitness_center.config;

import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> client = myUserRepository.findMyUserByUsername(username);

        if (client.isPresent()) {
            var clientObj = client.get();

            return User.builder()
                    .username(clientObj.getUsername())
                    .password(clientObj.getPassword())
                    .roles(String.valueOf(clientObj.getRole()))
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
