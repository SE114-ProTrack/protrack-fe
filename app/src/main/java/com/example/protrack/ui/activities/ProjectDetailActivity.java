package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.protrack.R;
import com.example.protrack.data.ApiClient;
import com.example.protrack.data.ProjectService;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.databinding.ActivityProjectDetailBinding;
import com.example.protrack.model.Project;
import com.example.protrack.model.response.ProjectResponse;
import com.example.protrack.ui.fragments.ProjectMemberListFragment;
import com.example.protrack.ui.fragments.TaskListFragment;
import com.example.protrack.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailActivity extends AppCompatActivity {

    private ActivityProjectDetailBinding binding;

    private enum Tab {TASK, MEMBER}

    private Tab currentTab = Tab.TASK;
    private ProjectService projectService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectService = ApiClient.getInstance().create(ProjectService.class);

        setupListeners();
        setUpProjectDetail(getIntent().getStringExtra("projectId"));
        showTasks();
        replaceFragment(new TaskListFragment());
    }

    private void setUpProjectDetail(String projectId) {
        String token = "Bearer " + SharedPrefsManager.getInstance(this).getToken();

        projectService.getProjectDetail(token, projectId)
                .enqueue(new Callback<ProjectResponse>() {
                    @Override
                    public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ProjectResponse res = response.body();
                            Project p = new Project(
                                    res.getProjectId(),
                                    res.getProjectName(),
                                    res.getDescription(),
                                    res.getBannerUrl(),
                                    res.getCreateTime(),
                                    res.getCreatorFullName(),
                                    res.getAllTasks(),
                                    res.getCompletedTasks()
                            );

                            binding.projectName.setText(p.getTenDuAn());
                            binding.description.setText(Utils.limitString(p.getMoTa(), 36));
                            binding.completedTask.setText(String.valueOf(p.getTaskHoanThanh()));
                            binding.totalTask.setText("/" + p.getTongTask() + " task");
                            binding.progressBar.setProgress((int)((float)p.getTaskHoanThanh() / p.getTongTask() * 100));
                            binding.taskLeft.setText(p.getTongTask() - p.getTaskHoanThanh() + " task left");

                            Glide.with(binding.projectBanner.getContext())
                                    .load(p.getAnhBiaDuAn())
                                    .into(binding.projectBanner);

                            Log.i("TAG", "fetch data: " + response.body());
                        } else {
                            Toast.makeText(ProjectDetailActivity.this, "Không tải được dự án", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProjectResponse> call, Throwable t) {
                        Toast.makeText(ProjectDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

            Intent intent = new Intent(ProjectDetailActivity.this, AddProjectMemberActivity.class);
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