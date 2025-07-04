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
import android.widget.Toast;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.data.TaskService;
import com.example.protrack.databinding.FragmentTaskListBinding;
import com.example.protrack.model.Task;
import com.example.protrack.model.response.TaskResponse;
import com.example.protrack.ui.activities.TaskDetailActivity;
import com.example.protrack.ui.adapters.TaskListAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;

public class TaskListFragment extends Fragment {
    private FragmentTaskListBinding taskListBinding;
    private TaskListAdapter taskAdapter;

    public void setTasks(List<Task> tasks) {
        taskAdapter.setTasks(tasks);
    }

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
        loadTasksFromApi();
    }

    private void setupTaskList() {
        taskAdapter = new TaskListAdapter(new ArrayList<>(), task -> {
            Intent intent = new Intent(requireContext(), TaskDetailActivity.class);
            // Bạn có thể truyền id hoặc object task qua intent nếu muốn
            startActivity(intent);
        });

        taskListBinding.getRoot().setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        taskListBinding.taskList.setAdapter(taskAdapter);
    }

    private void loadTasksFromApi() {
        String token = SharedPrefsManager.getInstance(getContext()).getToken();
        TaskService api = ApiClient.getInstance().create(TaskService.class);
        api.getTasksByUser("Bearer " + token).enqueue(new retrofit2.Callback<List<TaskResponse>>() {
            @Override
            public void onResponse(Call<List<TaskResponse>> call, retrofit2.Response<List<TaskResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Task> taskList = new ArrayList<>();
                    for (TaskResponse t : response.body()) {
                        taskList.add(new Task(
                                t.getTaskId(),
                                t.getTaskName(),
                                t.getDescription(),
                                t.getDeadline() != null ? LocalDate.parse(t.getDeadline().substring(0,10)) : null,
                                t.getStatus(),
                                null, // labelId
                                null, // attachment
                                new ArrayList<>(),
                                t.getIcon(),
                                t.getColor(),
                                t.getProjectName()
                        ));
                    }
                    taskAdapter.setTasks(taskList);
                }
            }
            @Override
            public void onFailure(Call<List<TaskResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi tải task: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskListBinding = null;
    }
}
