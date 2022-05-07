package com.bolshakov.diploma.models;

public class User {
    public String email;
    public String membership;

    public User(String email, String membership) {
        this.email = email;
        this.membership = membership;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", membership='" + membership + '\'' +
                '}';
    }
}
