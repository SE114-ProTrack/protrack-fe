package com.example.protrack.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.protrack.databinding.FragmentTabOverviewBinding;
import com.example.protrack.model.Project;
import com.example.protrack.model.Task;
import com.example.protrack.ui.activities.ProjectDetailActivity;
import com.example.protrack.ui.activities.ProjectListActivity;
import com.example.protrack.ui.activities.TaskDetailActivity;
import com.example.protrack.ui.activities.TaskListActivity;
import com.example.protrack.ui.adapters.ProjectCarouselAdapter;
import com.example.protrack.ui.adapters.TaskListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabOverviewFragment extends Fragment {

    private FragmentTabOverviewBinding overviewTabBinding;
    private ProjectCarouselAdapter projectAdapter;
    private TaskListAdapter taskAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        overviewTabBinding = FragmentTabOverviewBinding.inflate(inflater, container, false);
        return overviewTabBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupProjectCarousel();
        setupTaskList();
        loadMockProjects(); // Có thể thay bằng API sau này
        loadMockTasks();

        overviewTabBinding.seeProjectListButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProjectListActivity.class);
            startActivity(intent);
        });

        overviewTabBinding.seeTaskListButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TaskListActivity.class);
            startActivity(intent);
        });
    }

    private void setupProjectCarousel() {
        projectAdapter = new ProjectCarouselAdapter(new ArrayList<>(), project -> {
            // TODO: Xử lý khi click vào một project
            Intent intent = new Intent(requireContext(), ProjectDetailActivity.class);
            startActivity(intent);
        });

        overviewTabBinding.projectCarousel.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        overviewTabBinding.projectCarousel.setAdapter(projectAdapter);
    }

    private void setupTaskList() {
        taskAdapter = new TaskListAdapter(new ArrayList<>(), task -> {
            // TODO: Xử lý khi click vào một task
            Intent intent = new Intent(requireContext(), TaskDetailActivity.class);
            startActivity(intent);
        });

        overviewTabBinding.taskList.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        overviewTabBinding.taskList.setAdapter(taskAdapter);
    }

    private void loadMockProjects() {
        List<Project> mockList = new ArrayList<>();
        mockList.add(new Project(1l, "Marketing Plan", "Some description about this project", "Jan 1, 2025", 1l));
        mockList.add(new Project(2l, "UI Redesign", "Some description about this project", "Mar 19, 2025", 2l));
        mockList.add(new Project(3l, "Sprint Planning", "Some description about this project", "Oct 31, 2025", 3l));
        projectAdapter.setProjects(mockList);
    }

    private void loadMockTasks() {
        List<Task> mockList = new ArrayList<>();
        mockList.add(new Task("Wireframe - ProTrack", "Design", "SE332"));
        mockList.add(new Task("Wireframe - ProTrack", "Design", "SE332"));
        taskAdapter.setTasks(mockList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        overviewTabBinding = null;
    }
}
