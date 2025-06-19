package com.example.protrack.ui.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.protrack.R;
import com.example.protrack.model.Task;
import com.example.protrack.model.Comment;
import java.util.ArrayList;
import java.util.List;

public class TaskDetailActivity extends AppCompatActivity {

    private List<Task> taskList;
    private List<Comment> commentList;
    private int visibleCount = 2; // Số comment hiển thị ban đầu

    private Button btnExpand;
    private Button btnEditTask;
    private ImageButton searchButton;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Khởi tạo danh sách subtask mẫu
        taskList = new ArrayList<>();
        taskList.add(new Task("Wireframe - ProTrack", "Design", "SE332"));
        taskList.add(new Task("Create backend API", "Code", "SE333"));

        // Gán adapter cho danh sách subtask
        ListView listViewSubtask = findViewById(R.id.listviewsubtask);
        TaskCardAdapter subTaskAdapter = new TaskCardAdapter(this, taskList);
        listViewSubtask.setAdapter(subTaskAdapter);

        // Khởi tạo danh sách comment mẫu
        commentList = new ArrayList<>();
        commentList.add(new Comment("Tiến Tôm", "3 hours ago", "Hello everyone, I have updated some tasks. Please take a look and reply to me asap."));
        commentList.add(new Comment("Mai Lan", "1 hour ago", "Thanks for the update. I'll check it now."));
        commentList.add(new Comment("Anh Tuấn", "Just now", "Noted. Will get back to you soon."));

        // Gán adapter cho danh sách comment
        ListView listViewComment = findViewById(R.id.listviewComment);
        CommentCardAdapter commentCardAdapter = new CommentCardAdapter(this, commentList, visibleCount);
        listViewComment.setAdapter(commentCardAdapter);

        // Nút mở rộng comment
        btnExpand = findViewById(R.id.btn_cmt_expand);
        btnExpand.setOnClickListener(v -> {
            commentCardAdapter.increaseVisibleItemCount(2); // Hiển thị thêm 2 comment khi nhấn nút
        });

//        // Nút chuyển sang màn hình chỉnh sửa task
//        btnEditTask = findViewById(R.id.btn_edit);
//        btnEditTask.setOnClickListener(v -> {
//            Intent intent = new Intent(TaskDetailActivity.this, EditTaskActivity.class);
//            startActivity(intent);
//        });

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


    }
}
