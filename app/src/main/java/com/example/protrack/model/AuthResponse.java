package com.example.protrack.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("idNguoiDung")
    private Long idNguoiDung;
    @SerializedName("hoTen")
    private String hoTen;
    @SerializedName("email")
    private String email;

    // Constructor
    public AuthResponse(String token, Long idNguoiDung, String hoTen, String email) {
        this.token = token;
        this.idNguoiDung = idNguoiDung;
        this.hoTen = hoTen;
        this.email = email;
    }

    // Getters
    public String getToken() { return token; }
    public Long getIdNguoiDung() { return idNguoiDung; }
    public String getHoTen() { return hoTen; }
    public String getEmail() { return email; }
}
