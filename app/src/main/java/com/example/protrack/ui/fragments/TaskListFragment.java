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
import java.util.UUID;

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

        mockList.add(new Task(
                UUID.randomUUID().toString(),          // id
                "Wireframe - ProTrack",                // title
                "Thiết kế UI phần đầu",                // description
                LocalDate.of(2025, 7, 1),              // dueDate
                "Not Started",                         // status
                "SE332",                               // labelId
                null,                                  // attachment (có thể là null nếu chưa dùng)
                new ArrayList<>(),                     // assigneeIds
                "ic_design",                           // icon (string tên icon)
                "#FFA500",                             // color (hex)
                "ProTrack Project"                     // projectName
        ));


        mockList.add(new Task(
                UUID.randomUUID().toString(),
                "Database Schema",
                "Thiết kế sơ đồ CSDL",
                LocalDate.of(2025, 7, 10),
                "In Progress",
                "SE332",
                null,
                new ArrayList<>(),
                "ic_db",
                "#33A1FD",
                "ProTrack Project"
        ));

        taskAdapter.setTasks(mockList);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskListBinding = null;
    }
}