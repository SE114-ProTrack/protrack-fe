package com.example.protrack.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.protrack.R;
import com.example.protrack.databinding.ActivityProjectDetailBinding;
import com.example.protrack.ui.fragments.ProjectMemberListFragment;
import com.example.protrack.ui.fragments.TaskListFragment;

public class ProjectDetailActivity extends AppCompatActivity {

    private ActivityProjectDetailBinding binding;

    private enum Tab {TASK, MEMBER}

    private Tab currentTab = Tab.TASK;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.taskTab.setOnClickListener(v -> showTasks());
        binding.memberTab.setOnClickListener(v -> showMembers());

        showTasks();
        replaceFragment(new TaskListFragment());
    }

    private void showTasks() {
        if (currentTab == Tab.TASK) return;
        currentTab = Tab.TASK;
        binding.taskTab.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_contained_14));
        binding.memberTab.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_transparent));
        binding.taskTab.setTextColor(getResources().getColor(R.color.colorOnPrimary, null));
        binding.memberTab.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        binding.taskTab.setAlpha(1.0F);
        binding.memberTab.setAlpha(0.5F);

        replaceFragment(new TaskListFragment());
    }

    private void showMembers() {
        if (currentTab == Tab.MEMBER) return;
        currentTab = Tab.MEMBER;
        binding.memberTab.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_contained_14));
        binding.taskTab.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_transparent));
        binding.memberTab.setTextColor(getResources().getColor(R.color.colorOnPrimary, null));
        binding.taskTab.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        binding.taskTab.setAlpha(0.5F);
        binding.memberTab.setAlpha(1.0F);

        replaceFragment(new ProjectMemberListFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}