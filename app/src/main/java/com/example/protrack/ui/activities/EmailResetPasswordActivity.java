package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.databinding.ActivityEmailResetPasswordBinding;

public class EmailResetPasswordActivity extends AppCompatActivity {

    private ActivityEmailResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(EmailResetPasswordActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

    }
}