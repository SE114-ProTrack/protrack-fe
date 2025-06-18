package com.example.protrack.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.ApiService;
import com.example.protrack.model.RegisterRequest;
import com.example.protrack.model.AuthResponse;
import com.example.protrack.databinding.ActivityRegisterBinding;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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

        // mở date picker
        binding.dateOfBirthInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Định dạng lại ngày
                        String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                        binding.dateOfBirthInput.setText(formattedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
    }

    private void registerUser() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordInput.getText().toString().trim();
        String fullName = binding.fullNameInput.getText().toString().trim();
        String dob = binding.dateOfBirthInput.getText().toString().trim();
        String gender = "nam";
        String phone = binding.phoneNumberInput.getText().toString().trim();
        String address = binding.addressInput.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.setError("Invalid email format");
            return;
        } else {
            binding.emailLayout.setError(null);
        }

        if (password.length() < 8) {
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

        RegisterRequest request = new RegisterRequest(email, password, fullName, dob, gender, phone, address);

        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        binding.signUpButton.setEnabled(false);

        apiService.register(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                binding.signUpButton.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    finish(); // quay lại LoginActivity
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                binding.signUpButton.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
