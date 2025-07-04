package com.example.protrack.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.ProjectService;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.databinding.FragmentTabOverviewBinding;
import com.example.protrack.model.Project;
import com.example.protrack.model.Task;
import com.example.protrack.model.response.PageProjectResponse;
import com.example.protrack.model.response.ProjectResponse;
import com.example.protrack.model.response.TaskResponse;
import com.example.protrack.data.TaskService;
import com.example.protrack.ui.activities.ProjectDetailActivity;
import com.example.protrack.ui.activities.ProjectListActivity;
import com.example.protrack.ui.activities.TaskDetailActivity;
import com.example.protrack.ui.activities.TaskListActivity;
import com.example.protrack.ui.adapters.ProjectCarouselAdapter;
import com.example.protrack.ui.adapters.TaskListAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabOverviewFragment extends Fragment {

    private FragmentTabOverviewBinding overviewTabBinding;
    private ProjectCarouselAdapter projectAdapter;
    private TaskListAdapter taskAdapter;
    private ProjectService projectService;
    private List<Project> projectList = new ArrayList<>();

    private TaskService taskService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        overviewTabBinding = FragmentTabOverviewBinding.inflate(inflater, container, false);
        return overviewTabBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        projectService = ApiClient.getInstance().create(ProjectService.class);
        taskService = ApiClient.getInstance().create(TaskService.class);

        setupProjectCarousel();
        setupTaskList();
        load3Projects();
        loadUserTasksFromApi();

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
        projectAdapter = new ProjectCarouselAdapter(projectList, project -> {
            // TODO: Xử lý khi click vào một project
            Intent intent = new Intent(requireContext(), ProjectDetailActivity.class);
            intent.putExtra("projectId", project.getId());
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

    private void load3Projects() {
        String token = "Bearer " + SharedPrefsManager.getInstance(getContext()).getToken();

        projectService.get3Projects(token)
                .enqueue(new Callback<List<ProjectResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProjectResponse>> call, Response<List<ProjectResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear danh sách cũ
                            projectList.clear();

                            // Convert từng ProjectResponse sang Project
                            for (ProjectResponse res : response.body()) {
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

                            projectAdapter.notifyDataSetChanged(); // Cập nhật UI

                            Log.i("TAG", "fetch data: " + response.body());
                        } else {
                            Toast.makeText(getContext(), "Không tải được dự án", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProjectResponse>> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadUserTasksFromApi() {
        String token = "Bearer " + SharedPrefsManager.getInstance(getContext()).getToken();
        taskService.getTasksByUser(token).enqueue(new Callback<List<TaskResponse>>() {
            @Override
            public void onResponse(Call<List<TaskResponse>> call, Response<List<TaskResponse>> response) {
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
                } else {
                    Toast.makeText(getContext(), "Không tải được task", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<TaskResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        overviewTabBinding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        load3Projects();
        loadUserTasksFromApi();
    }
}
