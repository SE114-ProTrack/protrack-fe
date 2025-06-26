package com.example.protrack.ui.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {
    private MaterialButton btnNotStarted, btnInProgress, btnCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        // Khởi tạo 3 nút trạng thái công việc
        btnNotStarted = findViewById(R.id.btnNotStarted);
        btnInProgress = findViewById(R.id.btnInProgress);
        btnCompleted = findViewById(R.id.btnCompleted);
        // Gom các nút vào danh sách để dễ xử lý chung
        List<MaterialButton> buttons = Arrays.asList(btnNotStarted, btnInProgress, btnCompleted);

        for (MaterialButton button : buttons) {
            button.setOnClickListener(v -> {
                for (MaterialButton b : buttons) {
                    // Đặt lại giao diện cho tất cả các nút về trạng thái mặc định
                    b.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                    b.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#26495C")));
                    b.setTextColor(Color.parseColor("#26495C"));
                    b.setTag(null);
                }

                // Tô màu cho nút đang được chọn
                MaterialButton selected = (MaterialButton) v;
                selected.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#26495C")));
                selected.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT));
                selected.setTextColor(Color.WHITE);
                selected.setTag("selected");
            });
        }
        // quay về
        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish(); // Quay lại màn trước đó (TaskDetailActivity)
        });
    }
}
