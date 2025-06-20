package com.example.protrack.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.protrack.R;
import com.example.protrack.ui.fragments.ChatFragment;
import com.example.protrack.ui.fragments.HomeFragment;
import com.example.protrack.ui.fragments.NotificationFragment;
import com.example.protrack.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout tabHome, tabChat, tabNotification, tabSettings;
    private ImageView iconHome, iconChat, iconNotification, iconSettings;

    private int primaryColor;
    private int defaultColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primaryColor = getResources().getColor(R.color.colorPrimary, null);
        defaultColor = getResources().getColor(R.color.colorDefaultIcon, null);

        initViews();
        setListeners();

        setActiveTab(tabHome);
        replaceFragment(new HomeFragment());
    }

    private void initViews() {
        tabHome = findViewById(R.id.tab_home);
        tabChat = findViewById(R.id.tab_chat);
        tabNotification = findViewById(R.id.tab_notification);
        tabSettings = findViewById(R.id.tab_settings);

        iconHome = tabHome.findViewById(R.id.icon);
        iconChat = tabChat.findViewById(R.id.icon);
        iconNotification = tabNotification.findViewById(R.id.icon);
        iconSettings = tabSettings.findViewById(R.id.icon);

        iconHome.setImageResource(R.drawable.ic_home);
        iconChat.setImageResource(R.drawable.ic_chat);
        iconNotification.setImageResource(R.drawable.ic_notification);
        iconSettings.setImageResource(R.drawable.ic_settings);

    }

    private void setListeners() {
        tabHome.setOnClickListener(v -> {
            setActiveTab(tabHome);
            replaceFragment(new HomeFragment());
        });
        tabChat.setOnClickListener(v -> {
            setActiveTab(tabChat);
            replaceFragment(new ChatFragment());
        });
        tabNotification.setOnClickListener(v -> {
            setActiveTab(tabNotification);
            replaceFragment(new NotificationFragment());
        });
        tabSettings.setOnClickListener(v -> {
            setActiveTab(tabSettings);
            replaceFragment(new SettingsFragment());
        });
    }

    private void setActiveTab(LinearLayout selectedTab) {
        resetTabColors();

        ImageView selectedIcon = selectedTab.findViewById(R.id.icon);
        selectedIcon.setColorFilter(primaryColor);
    }

    private void resetTabColors() {
        iconHome.setColorFilter(defaultColor);
        iconChat.setColorFilter(defaultColor);
        iconNotification.setColorFilter(defaultColor);
        iconSettings.setColorFilter(defaultColor);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
