package com.example.protrack.ui.activities;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.protrack.R;
import com.example.protrack.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Task> taskList;
    private TaskCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kích hoạt chế độ Edge-to-Edge để giao diện tràn ra viền màn hình
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        // Thiết lập khoảng cách cho phần tử chính để tránh đè lên thanh trạng thái, điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listview);

        // Tạo danh sách mẫu các task
        taskList = new ArrayList<>();
        taskList.add(new Task("Wireframe - ProTrack", "Design", "SE332", LocalDate.of(2025, 7, 1)));
        taskList.add(new Task("Wireframe - ProTrack", "Design", "SE332",LocalDate.of(2020, 10, 8)));

        // Gán adapter để hiển thị task trong ListView
        adapter = new TaskCardAdapter(this, taskList);
        listView.setAdapter(adapter);

        // Xử lý sự kiện click vào từng task để mở chi tiết
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = taskList.get(position);
            Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
            intent.putExtra("task", selectedTask);  // Task implements Serializable
            startActivity(intent);
        });

//        // Tạo danh sách subtask (chưa được sử dụng trong adapter)
//        ArrayList<Task.SubTask> subTaskList = new ArrayList<>();
//        subTaskList.add(new Task.SubTask("Subtask 1"));
//        subTaskList.add(new Task.SubTask("Subtask 2"));
    }
}
