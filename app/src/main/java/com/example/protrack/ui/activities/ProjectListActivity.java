package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.ProjectService;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.databinding.ActivityProjectListBinding;
import com.example.protrack.model.Project;
import com.example.protrack.model.response.PageProjectResponse;
import com.example.protrack.model.response.ProjectResponse;
import com.example.protrack.ui.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectListActivity extends AppCompatActivity {

    private ActivityProjectListBinding binding;
    private ProjectListAdapter adapter;
    private ProjectService projectService;
    private List<Project> projectList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectService = ApiClient.getInstance().create(ProjectService.class);

        setupListeners();
        setupProjectList();
        loadProjects(0, 20);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProjects(0, 20);
    }

    private void setupListeners() {
        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

    }

    private void setupProjectList() {
        adapter = new ProjectListAdapter(projectList, project -> {
            // TODO: xử lý khi nhấn vào 1 project
            Intent intent = new Intent(ProjectListActivity.this, ProjectDetailActivity.class);
            intent.putExtra("projectId", project.getId());
            startActivity(intent);
        });

        binding.projectList.setLayoutManager(new LinearLayoutManager(this));
        binding.projectList.setAdapter(adapter);
    }

    private void loadProjects(int page, int size) {
        String token = "Bearer " + SharedPrefsManager.getInstance(this).getToken();

        projectService.getMyProjects(page, size, token)
                .enqueue(new Callback<PageProjectResponse>() {
                    @Override
                    public void onResponse(Call<PageProjectResponse> call, Response<PageProjectResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear danh sách cũ
                            projectList.clear();

                            // Convert từng ProjectResponse sang Project
                            for (ProjectResponse res : response.body().getContent()) {
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
                                projectList.add(p);
                            }

                            adapter.notifyDataSetChanged(); // Cập nhật UI

                            Log.i("TAG", "fetch data: " + response.body().getContent());
                        } else {
                            Toast.makeText(ProjectListActivity.this, "Không tải được dự án", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PageProjectResponse> call, Throwable t) {
                        Toast.makeText(ProjectListActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}