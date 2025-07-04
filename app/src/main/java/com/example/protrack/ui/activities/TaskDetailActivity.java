package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.data.ApiClient;
import com.example.protrack.data.CommentService;
import com.example.protrack.data.LabelService;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.data.TaskService;
import com.example.protrack.model.Task;
import com.example.protrack.model.Comment;
import com.example.protrack.model.response.CommentPageResponse;
import com.example.protrack.model.response.CommentResponse;
import com.example.protrack.model.response.LabelResponse;
import com.example.protrack.model.response.TaskAttachmentResponse;
import com.example.protrack.model.response.TaskResponse;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        String taskId = getIntent().getStringExtra("taskId");
        String token = SharedPrefsManager.getInstance(this).getToken();

        // Lấy task detail từ API
        TaskService api = ApiClient.getInstance().create(TaskService.class);
        api.getTaskDetail("Bearer " + token, taskId).enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TaskResponse task = response.body();
                    showTaskDetail(task);

                    // Hiển thị label (danh sách)
                    if (task.getLabels() != null && !task.getLabels().isEmpty()) {
                        showLabels(task.getLabels());
                    }

                    // Hiển thị tập đính kèm (danh sách)
                    if (task.getAttachments() != null && !task.getAttachments().isEmpty()) {
                        showAttachments(task.getAttachments());
                    }

                    // Lấy comment từ API (nếu chưa có trong task)
                    fetchComments(taskId);
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(TaskDetailActivity.this, "Lỗi tải chi tiết task: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // ... (code xử lý các view, recyclerViewComment, btnExpand, btnEditTask, search, back, gửi comment ... giữ nguyên)

        // Gửi comment mới (gọi API thật nếu có)
        sendIcon.setOnClickListener(v -> {
            String content = commentInput.getText().toString().trim();
            if (!content.isEmpty()) {
                // TODO: Gọi API post comment thay vì chỉ add vào list
                // Sau khi thành công, fetch lại comment từ API
                //postComment(taskId, content);
                commentInput.setText("");
            }
        });
    }

    private void showTaskDetail(TaskResponse task) {
        // setText cho tên, mô tả, deadline, status, project name, icon, v.v.
        // Ví dụ:
        //TextView name = findViewById(R.id.task_name);
        //name.setText(task.getTaskName());
        // ... các trường khác
    }

    private void showLabels(List<LabelResponse> labels) {
//        ChipGroup chipGroup = findViewById(R.id.chipGroupLabel);
//        chipGroup.removeAllViews();
//        for (LabelResponse label : labels) {
//            Chip chip = new Chip(this);
//            chip.setText(label.getLabelName());
//            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(label.getColor())));
//            chipGroup.addView(chip);
//        }
    }

    private void showAttachments(List<TaskAttachmentResponse> attachments) {
        // Hiển thị file đính kèm ra RecyclerView hoặc ListView
        // Adapter custom để show tên file, icon tải về, v.v.
    }

    private void fetchComments(String taskId) {
        CommentService api = ApiClient.getInstance().create(CommentService.class);
        String token = SharedPrefsManager.getInstance(this).getToken();
        api.getCommentsByTask("Bearer " + token, taskId, 0, 50).enqueue(new Callback<CommentPageResponse>() {
            @Override
            public void onResponse(Call<CommentPageResponse> call, Response<CommentPageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CommentResponse> comments = response.body().getContent();
                    showComments(comments);
                }
            }

            @Override
            public void onFailure(Call<CommentPageResponse> call, Throwable t) {
            }
        });
    }

    private void showComments(List<CommentResponse> comments) {
        // Map về model Comment, update Adapter commentCardAdapter
        List<Comment> commentList = new ArrayList<>();
        for (CommentResponse c : comments) {
            commentList.add(new Comment(
                    c.getUserName(),
                    c.getTimestamp().toString(), // Xử lý format date nếu cần
                    c.getContent(),
                    R.drawable.ic_launcher_background // hoặc avatar nếu có
            ));
        }
        //commentCardAdapter.setData(commentList);
        recyclerViewComment.scrollToPosition(commentList.size() - 1);
    }
}

