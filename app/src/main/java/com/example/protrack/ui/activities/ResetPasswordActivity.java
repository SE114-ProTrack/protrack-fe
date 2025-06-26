package com.example.protrack.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

    }
}