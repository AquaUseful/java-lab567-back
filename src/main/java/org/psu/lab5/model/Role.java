package org.psu.lab5.model;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    EDITOR("EDITOR"),
    USER("USER");

    private final String name;

    @Override
    public String getAuthority() {
        return name;
    }
}