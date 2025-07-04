package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.data.ApiClient;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.model.Task;
import com.example.protrack.model.response.TaskResponse;
import com.example.protrack.data.TaskService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskListActivity extends AppCompatActivity {
    private static final int REQUEST_EDIT_TASK = 100;
    private static final int REQUEST_CREATE_TASK = 101;
    private FloatingActionButton fabAddTask;
    private RecyclerView recyclerView;
    private ArrayList<Task> taskList;
    private TaskCardAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, EditTaskActivity.class);
            intent.putExtra("mode", "create");
            startActivityForResult(intent, REQUEST_CREATE_TASK);
        });

        // KHỞI TẠO danh sách rỗng, sẽ lấy thật từ API
        taskList = new ArrayList<>();

        recyclerView = findViewById(R.id.taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskCardAdapter(taskList, this, task -> {
            Intent intent = new Intent(TaskListActivity.this, EditTaskActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("task", task); // Task implements Serializable
            startActivityForResult(intent, REQUEST_EDIT_TASK);
        });
        recyclerView.setAdapter(taskAdapter);

        // Nút quay về
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Gọi API thật để load danh sách task
        loadTasksFromApi();
    }

    private void loadTasksFromApi() {
        String token = SharedPrefsManager.getInstance(this).getToken();
        TaskService api = ApiClient.getInstance().create(TaskService.class);

        api.getTasksByUser("Bearer " + token).enqueue(new Callback<List<TaskResponse>>() {
            @Override
            public void onResponse(Call<List<TaskResponse>> call, Response<List<TaskResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskList.clear(); // Xóa data cũ
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
                    taskAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TaskListActivity.this, "Không tải được danh sách task", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TaskResponse>> call, Throwable t) {
                Toast.makeText(TaskListActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Task updatedTask = (Task) data.getSerializableExtra("task");
            if (requestCode == REQUEST_CREATE_TASK) {
                taskList.add(updatedTask);
            } else if (requestCode == REQUEST_EDIT_TASK) {
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getId().equals(updatedTask.getId())) {
                        taskList.set(i, updatedTask);
                        break;
                    }
                }
            }
            taskAdapter.notifyDataSetChanged();
        }
    }
}
