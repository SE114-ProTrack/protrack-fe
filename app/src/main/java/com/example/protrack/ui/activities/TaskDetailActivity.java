package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Task;
import com.example.protrack.model.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDetailActivity extends AppCompatActivity {

    private List<Task> taskList;
    private List<Comment> commentList;
    private int visibleCount = 2; // Số comment hiển thị ban đầu

    private Button btnExpand;
    private Button btnEditTask;
    private EditText commentInput;
    private ImageButton sendIcon;
    private ImageView seeSubTaskActivity;
    private ImageButton searchButton;
    private EditText searchBar;
    private RecyclerView recyclerViewComment;
    private CommentCardAdapter commentCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

//        // Khởi tạo danh sách subtask mẫu
//        taskList = new ArrayList<>();
//        taskList.add(new Task("Wireframe - ProTrack", "Design", "SE332", LocalDate.of(2025, 7, 1 )));
//        taskList.add(new Task("Create backend API", "Code", "SE333",LocalDate.of(2020, 10, 1 )));
//
//        RecyclerView recyclerViewSubtask = findViewById(R.id.listviewsubtask);
//        recyclerViewSubtask.setLayoutManager(new LinearLayoutManager(this));
//
//// Nếu dùng TaskCardAdapter cho subtask
//        TaskCardAdapter subTaskAdapter = new TaskCardAdapter(taskList, task -> {
//            // Có thể để trống hoặc xử lý click subtask nếu muốn
//        });
//        recyclerViewSubtask.setAdapter(subTaskAdapter);


         seeSubTaskActivity = findViewById(R.id.seeSubTaskActivity);
        seeSubTaskActivity.setOnClickListener(v -> {
            Intent intent = new Intent(TaskDetailActivity.this, SubTaskListActivity.class);
            startActivity(intent);
        });



        // Khởi tạo danh sách comment mẫu
        commentList = new ArrayList<>();
        commentList.add(new Comment("Tiến Tôm", "3 hours ago", "Hello everyone, I have updated some tasks. Please take a look and reply to me asap.", R.drawable.bg_contained_14));
        commentList.add(new Comment("Mai Lan", "1 hour ago", "Thanks for the update. I'll check it now.", R.drawable.ic_launcher_background));
        commentList.add(new Comment("Anh Tuấn", "Just now", "Noted. Will get back to you soon.", R.drawable.bg_contained_14));

        // Gán adapter cho danh sách comment

        recyclerViewComment = findViewById(R.id.recyclerviewComment);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        commentCardAdapter = new CommentCardAdapter(this, commentList, visibleCount);
        recyclerViewComment.setAdapter(commentCardAdapter);


        // Nút mở rộng comment
        btnExpand = findViewById(R.id.btn_cmt_expand);
        btnExpand.setOnClickListener(v -> {
            commentCardAdapter.increaseVisibleItemCount(2); // Hiển thị thêm 2 comment khi nhấn nút
        });

        // Nút chuyển sang màn hình chỉnh sửa task
        btnEditTask = findViewById(R.id.btn_edit);
        btnEditTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskDetailActivity.this, EditTaskActivity.class);
            startActivity(intent);
        });



        // Xử lý tìm kiếm
        searchButton = findViewById(R.id.searchButton);
        searchBar = findViewById(R.id.searchBar);

        searchButton.setOnClickListener(v -> {
            if (searchBar.getVisibility() == View.INVISIBLE) {
                searchBar.setVisibility(View.VISIBLE);
                searchBar.requestFocus();
            } else {
                searchBar.setVisibility(View.INVISIBLE);
            }
        });

        // quay về
        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish(); // Quay lại màn trước đó (TaskListActivity)
        });

        // Gửi comment mới
        commentInput = findViewById(R.id.comment_input);
        sendIcon = findViewById(R.id.send_icon);


        sendIcon.setOnClickListener(v -> {
            String content = commentInput.getText().toString().trim();
            if (!content.isEmpty()) {
                Comment newComment = new Comment(
                        "You",
                        "Just now",
                        content,
                        R.drawable.ic_launcher_background
                );
                commentList.add(newComment);

                // TĂNG visibleCount rồi set lại để đảm bảo comment mới được hiển thị
                visibleCount++;
                commentCardAdapter.setVisibleCount(visibleCount);

                recyclerViewComment.post(() -> recyclerViewComment.scrollToPosition(commentList.size() - 1));
                commentInput.setText("");
            }
        });


    }
}
