package com.example.fitness_center.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    CLIENT, TRAINER, ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
