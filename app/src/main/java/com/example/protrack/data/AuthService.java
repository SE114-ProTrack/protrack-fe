package com.example.protrack.data;

import com.example.protrack.model.AuthResponse;
import com.example.protrack.model.LoginRequest;
import com.example.protrack.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<Void> register(@Body RegisterRequest registerRequest);
}
