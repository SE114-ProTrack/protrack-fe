package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.example.protrack.databinding.ActivityChangePasswordBinding;
import com.example.protrack.utils.Utils;

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

        binding.continueButton.setOnClickListener(v -> {

            // success
            Utils.showDialog(
                    this,
                    "Change Password\nSuccessfully!",
                    "Login again to continue",
                    "Login",
                    R.drawable.ic_check_circle,
                    view -> {
                        Intent intent = new Intent(this, AppIntroActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
            );
        });
    }
}