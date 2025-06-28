package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

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

        setupListeners();
        showTasks();
        replaceFragment(new TaskListFragment());
    }

    private void setupListeners() {
        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.moreButton.setOnClickListener(this::showMoreMenu);
        binding.taskTab.setOnClickListener(v -> showTasks());
        binding.memberTab.setOnClickListener(v -> showMembers());
    }

    private void showMoreMenu(View anchor) {
        View menuView = LayoutInflater.from(ProjectDetailActivity.this).inflate(R.layout.menu_project_detail, null);
        PopupWindow popupWindow = new PopupWindow(menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setElevation(10);
        popupWindow.showAsDropDown(anchor, -520, -10);

        menuView.findViewById(R.id.create_task).setOnClickListener(v -> {
            popupWindow.dismiss();

            Intent intent = new Intent(ProjectDetailActivity.this, EditTaskActivity.class);
            startActivity(intent);
        });

        menuView.findViewById(R.id.add_member).setOnClickListener(v -> {
            popupWindow.dismiss();

            Intent intent = new Intent(ProjectDetailActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });

        menuView.findViewById(R.id.edit_project).setOnClickListener(v -> {
            popupWindow.dismiss();

//            Intent intent = new Intent(ProjectDetailActivity.this, EditProjectActivity.class);
//            startActivity(intent);
        });

        menuView.findViewById(R.id.delete_project).setOnClickListener(v -> {
            popupWindow.dismiss();

            // logic delete project

            finish();
        });
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