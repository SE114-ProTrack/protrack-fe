package com.example.protrack.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.example.protrack.model.Chat;
import com.example.protrack.ui.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ImageButton btnSend;
    private String textString;
    private List<com.example.protrack.model.Chat> messageList;
    private ChatAdapter adapter;
    private ListView listView;
    private EditText typeMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        // Khởi tạo màn hình tin nhắn trống
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(this, messageList);
        listView = findViewById(R.id.listview);
        // Ánh xạ ô nhập tin nhắn từ layout activity_chat
        typeMessageEditText = findViewById(R.id.type_message_edittext);
        // Ánh xạ nút gửi tin nhắn từ layout activity_chat
        btnSend = findViewById(R.id.sendButton);
        listView.setAdapter(adapter);
        // Nút quay về
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        // Xử lý sự kiện khi nhấn nút gửi
        btnSend.setOnClickListener(v -> {
            String text = typeMessageEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                messageList.add(new Chat(text, true)); // true = gửi
                adapter.notifyDataSetChanged();
                typeMessageEditText.setText("");
                listView.setSelection(adapter.getCount() - 1);
            }
        });

    }
}