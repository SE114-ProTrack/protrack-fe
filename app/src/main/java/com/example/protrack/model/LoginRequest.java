package com.example.protrack.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("matKhau")
    private String matKhau;

    // Constructor
    public LoginRequest(String email, String matKhau) {
        this.email = email;
        this.matKhau = matKhau;
    }

    // Getters
    public String getEmail() { return email; }
    public String getMatKhau() { return matKhau; }
}
