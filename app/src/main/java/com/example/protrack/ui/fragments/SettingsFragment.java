package com.example.protrack.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.protrack.R;

import com.example.protrack.databinding.FragmentSettingsBinding;
import com.example.protrack.ui.activities.ProfileActivity;
import com.example.protrack.ui.activities.RegisterActivity;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();
    }

    private void setupListeners() {
        binding.moreButton.setOnClickListener(v -> showSettingsMenu(v));
    }
    private void showSettingsMenu(View anchor) {
        View menuView = LayoutInflater.from(getContext()).inflate(R.layout.menu_settings, null);
        PopupWindow popupWindow = new PopupWindow(menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setElevation(10);
        popupWindow.showAsDropDown(anchor, -530,-35);

        menuView.findViewById(R.id.edit_profile).setOnClickListener(v -> {
            popupWindow.dismiss();

            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        menuView.findViewById(R.id.help).setOnClickListener(v -> {
            popupWindow.dismiss();
            // TODO: Help
        });
        menuView.findViewById(R.id.logout).setOnClickListener(v -> {
            popupWindow.dismiss();
            // TODO: Logout
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}