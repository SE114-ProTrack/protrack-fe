package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.databinding.ActivityOtpBinding;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
    }

    private void setupListeners() {
        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

        EditText otp1 = binding.otp1, otp2 = binding.otp2, otp3 = binding.otp3,
                otp4 = binding.otp4, otp5 = binding.otp5;

        otp1.addTextChangedListener(new GenericTextWatcher(otp1, otp2));
        otp2.addTextChangedListener(new GenericTextWatcher(otp2, otp3));
        otp3.addTextChangedListener(new GenericTextWatcher(otp3, otp4));
        otp4.addTextChangedListener(new GenericTextWatcher(otp4, otp5));

    }
}

class GenericTextWatcher implements TextWatcher {
    private EditText currentView;
    private EditText nextView;

    public GenericTextWatcher(EditText currentView, EditText nextView) {
        this.currentView = currentView;
        this.nextView = nextView;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 1 && nextView != null) {
            nextView.requestFocus();
        }
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
}
