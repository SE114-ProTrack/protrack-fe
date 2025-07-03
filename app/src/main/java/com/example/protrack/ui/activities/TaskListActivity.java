package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

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
        fabAddTask=findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, EditTaskActivity.class);
            intent.putExtra("mode", "create");
            startActivityForResult(intent, REQUEST_CREATE_TASK);
        });
        taskList = new ArrayList<>();
        taskList.add(new Task(
                UUID.randomUUID().toString(),              // id
                "Wireframe - ProTrack",                    // title
                "Thiết kế UI đầu tiên",                    // description
                LocalDate.of(2025, 7, 1),                  // dueDate
                "Not Started",                             // status
                "SE332",                                   // labelId
                null,                                      // attachment
                new ArrayList<>(),                         // assigneeIds
                "ic_task",                                 // icon (tên icon)
                "#FF5733",                                 // color
                "ProTrack Project"                         // projectName
        ));
        taskList.add(new Task(
                UUID.randomUUID().toString(),
                "Fix bug animation",
                "Sửa lỗi chuyển cảnh khi đổi trạng thái task",
                LocalDate.of(2020, 10, 8),
                "Completed",
                "SE332",
                null,
                new ArrayList<>(),
                "ic_bug",
                "#00BCD4",
                "ProTrack Project"
        ));

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.taskList); // ID đúng của RecyclerView trong layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter với callback khi click
        taskAdapter = new TaskCardAdapter(taskList, this, task -> {
            Intent intent = new Intent(TaskListActivity.this, EditTaskActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("task", task); // Task implements Serializable
            startActivityForResult(intent, REQUEST_EDIT_TASK);
        });

        recyclerView.setAdapter(taskAdapter);

        // Nút quay về
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
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
