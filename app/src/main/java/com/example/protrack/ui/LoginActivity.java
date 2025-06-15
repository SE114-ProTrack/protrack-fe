package com.example.protrack.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.ApiService;
import com.example.protrack.model.AuthResponse;
import com.example.protrack.model.LoginRequest;
import com.example.protrack.databinding.ActivityLoginBinding;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bắt sự kiện đăng nhập
        binding.loginButton.setOnClickListener(v -> attemptLogin());

        // quay về
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Điều hướng đến màn hình đăng ký
        binding.signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
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
        LoginRequest loginRequest = new LoginRequest(email, password);

        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<AuthResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();

                    // Lưu token vào SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    prefs.edit()
                            .putString("jwt_token", auth.getToken())
                            .putLong("id", auth.getIdNguoiDung())
                            .putString("fullName", auth.getHoTen())
                            .putString("email", auth.getEmail())
                            .apply();

                    // Chuyển sang ProjectListActivity
                    Intent intent = new Intent(LoginActivity.this, ProjectListActivity.class);
                    startActivity(intent);
                    finish();
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