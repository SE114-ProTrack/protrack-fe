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

import com.example.protrack.R;
import com.example.protrack.model.ChatSummary;
import com.example.protrack.model.Task;
import com.example.protrack.ui.activities.ChatActivity;
import com.example.protrack.ui.activities.TaskDetailActivity;
import com.example.protrack.ui.activities.TaskListActivity;
import com.example.protrack.ui.adapters.ChatListAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    private RecyclerView chatlistRecyclerView;
    private ChatListAdapter chatListAdapter;
    private List<ChatSummary> chatSummaries;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo RecyclerView và dữ liệu
        chatlistRecyclerView = view.findViewById(R.id.chatlistRecycleView);
        chatlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu giả (dummy data) để hiển thị

        chatSummaries = new ArrayList<>();
        chatSummaries.add(new ChatSummary(R.drawable.ic_user, "John", "Hey, how are you?", "2m ago", 1));
        chatSummaries.add(new ChatSummary(R.drawable.ic_user, "Jane", "Let's meet tomorrow!", "10m ago", 0));
        chatSummaries.add(new ChatSummary(R.drawable.ic_user, "Team Project", "Update: deadline changed", "1h ago", 3));
//        Ví dụ tạo dữ liệu với ảnh từ URL:
//                chatSummaries = new ArrayList<>();
//        chatSummaries.add(new ChatSummary(
//                "https://example.com/avatar/john.jpg", // URL ảnh
//                "John",
//                "Hey, how are you?",
//                "2m ago",
//                1));

        // Gán adapter và truyền sự kiện click
        chatListAdapter = new ChatListAdapter(getContext(), chatSummaries, chatSummary -> {
            // Xử lý khi click vào 1 item
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("senderName", chatSummary.getSenderName());
            startActivity(intent);
        });

        chatlistRecyclerView.setAdapter(chatListAdapter);
    }

}