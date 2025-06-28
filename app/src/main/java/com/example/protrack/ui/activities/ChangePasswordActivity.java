package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
    }

    private void setupListeners() {
        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}