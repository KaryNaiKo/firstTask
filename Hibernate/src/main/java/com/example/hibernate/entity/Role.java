package com.example.hibernate.entity;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public String getName() {
        return name();
    }
}