package com.example.protrack.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.example.protrack.data.ApiClient;
import com.example.protrack.data.Message;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.model.Chat;
import com.example.protrack.model.ChatHeader;
import com.example.protrack.model.ChatItem;
import com.example.protrack.model.request.MessageRequest;
import com.example.protrack.model.response.MessagePage;
import com.example.protrack.model.response.MessageResponse;
import com.example.protrack.realtime.StompManager;
import com.example.protrack.ui.adapters.ChatAdapter;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private ListView listView;
    private List<ChatItem> messageList;
    private ChatAdapter adapter;
    private String chatWithUserId, currentUserId;
    private EditText typeMessageEditText;
    private ImageButton btnSend;

    private String lastDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Set tên user chat
        String chatWithUserName = getIntent().getStringExtra("userName");
        TextView userNameView = findViewById(R.id.userName);
        if (chatWithUserName != null) userNameView.setText(chatWithUserName);

        // Lấy id người đang chat và user hiện tại
        chatWithUserId = getIntent().getStringExtra("userId");
        currentUserId = SharedPrefsManager.getInstance(this).getUserId();
        Log.d("ChatDebug", "currentUserId=" + currentUserId + ", chatWithUserId=" + chatWithUserId);
        String token = SharedPrefsManager.getInstance(this).getToken();

        // ListView và Adapter
        listView = findViewById(R.id.listview);
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(this, messageList);
        listView.setAdapter(adapter);

        // Gửi tin nhắn
        typeMessageEditText = findViewById(R.id.type_message_edittext);
        btnSend = findViewById(R.id.sendButton);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        btnSend.setOnClickListener(v -> {
            String text = typeMessageEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                sendMessage(chatWithUserId, text);
                typeMessageEditText.setText("");
            }
        });

        // Kết nối realtime
        String wsUrl = "ws://10.0.2.2:8080/ws/websocket";
        StompManager.connect(wsUrl, token, new StompManager.StompConnectListener() {
            @Override
            public void onConnect() {
                String topic = "/topic/user." + currentUserId;
                StompManager.subscribe(topic, payload -> {
                    runOnUiThread(() -> {
                        MessageResponse msg = new Gson().fromJson(payload, MessageResponse.class);
                        boolean isSent = msg.getSenderId().equals(currentUserId);
                        Log.d("ChatDebug", "currentUserId=" + currentUserId + ", senderId=" + msg.getSenderId());
                        if (msg.getSenderId().equals(currentUserId) || msg.getSenderId().equals(chatWithUserId)) {
                            addMessageToList(msg);
                        }
                    });
                });
            }
            @Override public void onError(Throwable e) {}
            @Override public void onClose() {}
        });

        loadMessages();
    }

    private void loadMessages() {
        String token = SharedPrefsManager.getInstance(this).getToken();
        if (token == null || chatWithUserId == null) return;
        Message api = ApiClient.getInstance().create(Message.class);
        api.getConversationWithUser("Bearer " + token, chatWithUserId, 0, 50).enqueue(new Callback<MessagePage>() {
            @Override
            public void onResponse(Call<MessagePage> call, Response<MessagePage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MessageResponse> messages = response.body().getContent();
                    Collections.reverse(messages);
                    messageList.clear();
                    lastDate = ""; // <--- reset tại đây
                    for (MessageResponse msg : messages) {
                        String msgDate = msg.getSentAt().substring(0, 10);
                        if (!msgDate.equals(lastDate)) {
                            messageList.add(new ChatHeader(getHeaderLabel(msgDate)));
                            lastDate = msgDate;
                        }
                        boolean isSent = msg.getSenderId().equals(currentUserId);
                        Log.d("ChatDebug", "currentUserId=" + currentUserId + ", senderId=" + msg.getSenderId());
                        messageList.add(new Chat(msg.getContent(), isSent));
                    }
                    adapter.notifyDataSetChanged();
                    listView.setSelection(adapter.getCount() - 1);
                }
            }
            @Override
            public void onFailure(Call<MessagePage> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi tải tin nhắn: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMessageToList(MessageResponse msg) {
        String msgDate = msg.getSentAt().substring(0, 10);
        if (!msgDate.equals(lastDate)) {
            messageList.add(new ChatHeader(getHeaderLabel(msgDate)));
            lastDate = msgDate;
        }
        boolean isSent = msg.getSenderId().equals(currentUserId);
        messageList.add(new Chat(msg.getContent(), isSent));
        adapter.notifyDataSetChanged();
        listView.setSelection(adapter.getCount() - 1);
    }

    private String getHeaderLabel(String date) {
        LocalDate msgDay = LocalDate.parse(date);
        LocalDate today = LocalDate.now();
        if (msgDay.equals(today)) return "Today";
        if (msgDay.equals(today.minusDays(1))) return "Yesterday";
        return msgDay.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    private void sendMessage(String receiverId, String content) {
        String token = SharedPrefsManager.getInstance(this).getToken();
        Message api = ApiClient.getInstance().create(Message.class);
        MessageRequest request = new MessageRequest(receiverId, content);
        api.sendMessage("Bearer " + token, request).enqueue(new retrofit2.Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChatActivity.this, "Gửi tin nhắn thất bại", Toast.LENGTH_SHORT).show();
                }
                // KHÔNG tự add message vào list, chỉ chờ backend push qua WebSocket!
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi gửi tin nhắn: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StompManager.disconnect();
    }

    private String getCurrentUserId() {
        return SharedPrefsManager.getInstance(this).getUserId();
    }
}