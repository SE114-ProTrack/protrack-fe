package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.protrack.databinding.ActivityProjectListBinding;
import com.example.protrack.model.Project;
import com.example.protrack.ui.adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity {

    private ActivityProjectListBinding binding;
    private ProjectListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupProjectList();
        loadMockProjects(); // Sau này thay bằng API
    }

    private void setupProjectList() {
        adapter = new ProjectListAdapter(new ArrayList<>(), project -> {
            // TODO: xử lý khi nhấn vào 1 project
            Intent intent = new Intent(ProjectListActivity.this, ProjectDetailActivity.class);
            startActivity(intent);
        });

        binding.projectList.setLayoutManager(new LinearLayoutManager(this));
        binding.projectList.setAdapter(adapter);
    }

    private void loadMockProjects() {
        List<Project> mockProjects = new ArrayList<>();
        mockProjects.add(new Project(1L, "Marketing Plan", "Plan Q1 2025", "Jan 1, 2025", 1L));
        mockProjects.add(new Project(2L, "UI Redesign", "Redesign homepage", "Mar 10, 2025", 2L));
        mockProjects.add(new Project(3L, "Sprint Planning", "Sprint 12 tasks", "Apr 5, 2025", 3L));
        adapter.setProjects(mockProjects);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}