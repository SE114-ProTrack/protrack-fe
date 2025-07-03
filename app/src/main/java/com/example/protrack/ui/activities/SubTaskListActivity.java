package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.protrack.model.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubTaskListActivity extends AppCompatActivity {

    private RecyclerView subtaskRecyclerView;
    private FloatingActionButton fabAddTask;
    private ImageButton backButton;

    private List<Task> subtaskList;
    private TaskCardAdapter taskCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);

        // Ánh xạ view
        subtaskRecyclerView = findViewById(R.id.subtaskList);
        fabAddTask = findViewById(R.id.fabAddTask);
        backButton = findViewById(R.id.backButton);

        // Dữ liệu subtask mẫu
        subtaskList = new ArrayList<>();
        subtaskList.add(new Task(
                "1",                                     // id
                "Wireframe login screen",                // title
                "Create login screen wireframe",         // description
                LocalDate.of(2025, 7, 1),                // dueDate
                "In Progress",                           // status
                "label123",                              // labelId
                null,                                    // attachment
                new ArrayList<>(),                       // assigneeIds
                "📐",                                    // icon
                "#FFB800",                               // color
                "ProTrack"                               // projectName
        ));
        // Gán adapter
        taskCardAdapter = new TaskCardAdapter(subtaskList, this, task -> {
            Intent intent = new Intent(SubTaskListActivity.this, TaskDetailActivity.class);
            intent.putExtra("task", task);  // Task phải implements Serializable (bạn đã làm rồi)
            startActivity(intent);
        });



        subtaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subtaskRecyclerView.setAdapter(taskCardAdapter);

        // Xử lý nút quay lại
        backButton.setOnClickListener(v -> finish());

        // Xử lý nút thêm task
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(SubTaskListActivity.this, EditTaskActivity.class); // Nếu có
            startActivity(intent);
        });






    }
}
