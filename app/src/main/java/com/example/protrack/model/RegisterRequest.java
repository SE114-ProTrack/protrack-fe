package com.example.protrack.model;

public class RegisterRequest {
    private String email;
    private String password;

    // Constructor
    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
