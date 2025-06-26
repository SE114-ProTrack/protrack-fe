package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.databinding.ActivitySmsResetPasswordBinding;

public class SmsResetPasswordActivity extends AppCompatActivity {

    private ActivitySmsResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySmsResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(SmsResetPasswordActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

    }
}