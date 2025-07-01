package com.example.protrack.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.example.protrack.databinding.ActivityProfileBinding;
import com.example.protrack.model.UserProfile;
import com.example.protrack.utils.Utils;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "user";
    private ActivityProfileBinding binding;
    private boolean isEditMode = false;
    private UserProfile editingUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkMode();
        setupListeners();
    }

    private void checkMode() {
        if (getIntent().hasExtra(EXTRA_USER)) {
            isEditMode = true;
            editingUser = (UserProfile) getIntent().getSerializableExtra(EXTRA_USER);

            // Fill data for editing
            if (editingUser != null) {
                binding.fullNameInput.setText(editingUser.getHoTen());
                binding.phoneNumberInput.setText(editingUser.getDienThoai());
                binding.addressInput.setText(editingUser.getDiaChi());
            }
        }
    }

    private void setupListeners() {
        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
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

        // chọn giới tính
        binding.genderInput.setOnClickListener(this::showGenderMenu);

        // lưu thông tin
        binding.saveButton.setOnClickListener(v -> {
            String name = binding.fullNameInput.getText().toString().trim();
            String dob = binding.dateOfBirthInput.getText().toString().trim();
            String gender = binding.genderInput.getText().toString().trim();
            String phone = binding.phoneNumberInput.getText().toString().trim();
            String address = binding.addressInput.getText().toString().trim();

            // Validate rỗng
            if (name.isEmpty()) {
                binding.fullNameLayout.setError("Name is required");
                return;
            } else {
                binding.fullNameLayout.setError(null);
            }
            if (phone.isEmpty()) {
                binding.phoneNumberLayout.setError("Phone number is required");
                return;
            } else {
                binding.phoneNumberLayout.setError(null);
            }
            if (address.isEmpty()) {
                binding.addressLayout.setError("Address is required");
                return;
            } else {
                binding.addressLayout.setError(null);
            }

            if (isEditMode) {
                // Update user logic

            } else {
                // Create user logic

            }

            Utils.showDialog(
                    this,
                    "Register\nSuccessfully!",
                    "Your account is ready to use!",
                    "Start",
                    R.drawable.ic_check_circle,
                    view -> {
                        // xử lý sau khi nhấn OK
                        startActivity(new Intent(this, AppIntroActivity.class));
                        finish();
                    }
            );
        });
    }

    private void showGenderMenu(View anchor) {
        View menuView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.menu_select_gender, null);
        PopupWindow popupWindow = new PopupWindow(menuView,
                anchor.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setElevation(10);
        popupWindow.showAsDropDown(anchor, 0, 0);

        menuView.findViewById(R.id.male).setOnClickListener(v -> {
            popupWindow.dismiss();
            binding.genderInput.setText("Male");
        });

        menuView.findViewById(R.id.female).setOnClickListener(v -> {
            popupWindow.dismiss();
            binding.genderInput.setText("Female");
        });
    }
}