package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.ApiService;
import com.example.protrack.data.AuthService;
import com.example.protrack.model.RegisterRequest;
import com.example.protrack.model.AuthResponse;
import com.example.protrack.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // đăng ký
        binding.signUpButton.setOnClickListener(view -> registerUser());

        // quay về
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // chuyển đến màn hình đăng nhập
        binding.signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordInput.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.setError("Invalid email format");
            return;
        } else {
            binding.emailLayout.setError(null);
        }

        if (password.length() < 6) {
            binding.passwordLayout.setError("Password must be at least 8 characters");
            return;
        } else {
            binding.passwordLayout.setError(null);
        }

        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordLayout.setError("Passwords do not match");
            return;
        } else {
            binding.confirmPasswordLayout.setError(null);
        }

        binding.signUpButton.setEnabled(false);

        RegisterRequest request = new RegisterRequest(email, password);

        AuthService authService = ApiClient.getInstance().create(AuthService.class);

        authService.register(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                binding.signUpButton.setEnabled(true);
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                binding.signUpButton.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
