package com.example.protrack.model;

public class AuthResponse {
    private String token;
    private String userId;
    private String name;
    private String email;

    // Constructor
    public AuthResponse(String token, String userId, String name, String email) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getToken() { return token; }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
