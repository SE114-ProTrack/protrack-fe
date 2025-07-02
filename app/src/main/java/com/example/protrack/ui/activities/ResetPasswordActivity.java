package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.example.protrack.databinding.ActivityResetPasswordBinding;
import com.example.protrack.utils.Utils;

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

        binding.continueButton.setOnClickListener(v -> {
            Utils.showDialog(
                    this,
                    "Reset Password\nSuccessfully!",
                    "Login to start your journey",
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