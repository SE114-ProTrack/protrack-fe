package com.example.protrack.data;

import com.example.protrack.model.AuthResponse;
import com.example.protrack.model.LoginRequest;
import com.example.protrack.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest registerRequest);
}
