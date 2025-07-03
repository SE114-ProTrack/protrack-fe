package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.AuthService;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.model.AuthResponse;
import com.example.protrack.model.LoginRequest;
import com.example.protrack.databinding.ActivityLoginBinding;

import android.content.SharedPreferences;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bắt sự kiện đăng nhập
        binding.loginButton.setOnClickListener(v -> attemptLogin());

        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        // Điều hướng đến màn hình đăng ký
        binding.signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void attemptLogin() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();

        // Validate rỗng
        if (email.isEmpty()) {
            binding.emailLayout.setError("Email is required");
            return;
        } else {
            binding.emailLayout.setError(null);
        }
        if (password.isEmpty()) {
            binding.passwordLayout.setError("Password is required");
            return;
        } else {
            binding.passwordLayout.setError(null);
        }

        // Tạo request
        LoginRequest request = new LoginRequest(email, password);

        AuthService authService = ApiClient.getInstance().create(AuthService.class);

        authService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();

                    // Lưu token vào SharedPreferences
                    SharedPrefsManager prefs = SharedPrefsManager.getInstance(LoginActivity.this);

                    prefs.saveToken(auth.getToken());

                    // Chuyển sang MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Không quay lại Login
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}