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
import com.example.protrack.ui.activities.ProjectListActivity;
import com.example.protrack.ui.activities.TaskListActivity;
import com.example.protrack.ui.adapters.ProjectCarouselAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabOverviewFragment extends Fragment {

    private FragmentTabOverviewBinding binding;
    private ProjectCarouselAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTabOverviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupProjectCarousel();
        loadMockProjects(); // Có thể thay bằng API sau này

        binding.seeProjectListButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProjectListActivity.class);
            startActivity(intent);
        });

        binding.seeTaskListButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TaskListActivity.class);
            startActivity(intent);
        });
    }

    private void setupProjectCarousel() {
        adapter = new ProjectCarouselAdapter(new ArrayList<>(), project -> {
            // TODO: Xử lý khi click vào một project
        });

        binding.projectCarousel.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.projectCarousel.setAdapter(adapter);
    }

    private void loadMockProjects() {
        List<Project> mockList = new ArrayList<>();
        mockList.add(new Project(1l, "Marketing Plan", "Some description about this project", "Jan 1, 2025", 1l));
        mockList.add(new Project(2l, "UI Redesign", "Some description about this project", "Mar 19, 2025", 2l));
        mockList.add(new Project(3l, "Sprint Planning", "Some description about this project", "Oct 31, 2025", 3l));
        adapter.setProjects(mockList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
