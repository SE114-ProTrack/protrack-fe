package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;

    private enum Choice {SMS, EMAIL}

    private Choice currentChoice = Choice.SMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.smsChoice.setStrokeWidth(5);

        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.smsChoice.setOnClickListener(v -> {
            if (currentChoice == Choice.SMS) return;
            currentChoice = Choice.SMS;
            binding.smsChoice.setStrokeWidth(5);
            binding.emailChoice.setStrokeWidth(0);
        });

        binding.emailChoice.setOnClickListener(v -> {
            if (currentChoice == Choice.EMAIL) return;
            currentChoice = Choice.EMAIL;
            binding.smsChoice.setStrokeWidth(0);
            binding.emailChoice.setStrokeWidth(5);
        });

        binding.continueButton.setOnClickListener(v -> {
            Intent intent;
            if (currentChoice == Choice.SMS)
                intent = new Intent(ForgotPasswordActivity.this, SmsResetPasswordActivity.class);
            else
                intent = new Intent(ForgotPasswordActivity.this, EmailResetPasswordActivity.class);

            startActivity(intent);
        });
    }
}