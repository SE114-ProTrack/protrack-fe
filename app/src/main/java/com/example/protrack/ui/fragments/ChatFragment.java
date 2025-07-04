package com.example.protrack.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.protrack.R;
import com.example.protrack.data.ApiClient;
import com.example.protrack.data.Message;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.model.ChatSummary;
import com.example.protrack.model.Task;
import com.example.protrack.model.response.MessagePreviewResponse;
import com.example.protrack.ui.activities.ChatActivity;
import com.example.protrack.ui.activities.TaskDetailActivity;
import com.example.protrack.ui.activities.TaskListActivity;
import com.example.protrack.ui.adapters.ChatListAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView chatlistRecycleView;
    private ChatListAdapter adapter;
    private List<ChatSummary> chatSummaries = new ArrayList<>();
    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatlistRecycleView = view.findViewById(R.id.chatlistRecycleView);
        chatlistRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatListAdapter(getContext(), chatSummaries, item -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("userId", item.getUserId());
            intent.putExtra("userName", item.getSenderName());
            startActivity(intent);
        });
        chatlistRecycleView.setAdapter(adapter);
        loadChatList();
        return view;
    }

    private void loadChatList() {
        String token = SharedPrefsManager.getInstance(getContext()).getToken();
        if (token == null) return;
        Message api = ApiClient.getInstance().create(Message.class);
        api.getMessagePreviews("Bearer " + token).enqueue(new Callback<List<MessagePreviewResponse>>() {
            @Override
            public void onResponse(Call<List<MessagePreviewResponse>> call, Response<List<MessagePreviewResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatSummaries.clear();
                    for (MessagePreviewResponse preview : response.body()) {
                        ChatSummary summary = new ChatSummary(
                                R.drawable.ic_user, // avatarResId, bạn có thể dùng mặc định hoặc để riêng nếu có url
                                preview.getPartnerName(),  // userName: tên đối tác chat
                                preview.getMessageContent(), // lastMessage
                                preview.getSentAt(), // timeAgo (nếu muốn format, format ở đây)
                                preview.getUnreadCount(),
                                preview.getAvatarUrl(),
                                preview.getUserId()
                        );
                        chatSummaries.add(summary);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<MessagePreviewResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi tải danh sách chat: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Khởi tạo RecyclerView và dữ liệu
//        chatlistRecyclerView = view.findViewById(R.id.chatlistRecycleView);
//        chatlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Tạo dữ liệu giả (dummy data) để hiển thị
//
//        chatSummaries = new ArrayList<>();
//        chatSummaries.add(new ChatSummary(R.drawable.ic_user, "John", "Hey, how are you?", "2m ago", 1));
//        chatSummaries.add(new ChatSummary(R.drawable.ic_user, "Jane", "Let's meet tomorrow!", "10m ago", 0));
//        chatSummaries.add(new ChatSummary(R.drawable.ic_user, "Team Project", "Update: deadline changed", "1h ago", 3));
////        Ví dụ tạo dữ liệu với ảnh từ URL:
////                chatSummaries = new ArrayList<>();
////        chatSummaries.add(new ChatSummary(
////                "https://example.com/avatar/john.jpg", // URL ảnh
////                "John",
////                "Hey, how are you?",
////                "2m ago",
////                1));
//
//        // Gán adapter và truyền sự kiện click
//        chatListAdapter = new ChatListAdapter(getContext(), chatSummaries, chatSummary -> {
//            // Xử lý khi click vào 1 item
//            Intent intent = new Intent(getActivity(), ChatActivity.class);
//            intent.putExtra("senderName", chatSummary.getSenderName());
//            startActivity(intent);
//        });
//
//        chatlistRecyclerView.setAdapter(chatListAdapter);
//    }


}