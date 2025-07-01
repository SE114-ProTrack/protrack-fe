package com.example.protrack.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.protrack.databinding.FragmentTaskListBinding;
import com.example.protrack.model.Task;
import com.example.protrack.ui.activities.TaskDetailActivity;
import com.example.protrack.ui.adapters.TaskListAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding taskListBinding;
    private TaskListAdapter taskAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        taskListBinding = FragmentTaskListBinding.inflate(inflater, container, false);
        return taskListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupTaskList();
        loadMockTasks();

    }

    private void setupTaskList() {
        taskAdapter = new TaskListAdapter(new ArrayList<>(), task -> {
            // TODO: Xử lý khi click vào một task
            Intent intent = new Intent(requireContext(), TaskDetailActivity.class);
            startActivity(intent);
        });

        taskListBinding.getRoot().setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        taskListBinding.taskList.setAdapter(taskAdapter);
    }

    private void loadMockTasks() {
        List<Task> mockList = new ArrayList<>();
        mockList.add(new Task("Wireframe - ProTrack", "Design", "SE332", LocalDate.of(2025, 7, 1)));
        mockList.add(new Task("Wireframe - ProTrack", "Design", "SE332",LocalDate.of(2025, 7, 1)));
        taskAdapter.setTasks(mockList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskListBinding = null;
    }
}