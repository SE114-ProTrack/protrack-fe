package com.example.protrack.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.protrack.data.ApiClient;
import com.example.protrack.data.NotificationService;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.databinding.FragmentNotificationBinding;
import com.example.protrack.model.Notification;
import com.example.protrack.model.response.NotificationResponse;
import com.example.protrack.model.response.PageNotificationResponse;
import com.example.protrack.ui.adapters.NotificationAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private NotificationAdapter adapter;
    private NotificationService notificationService;
    private final List<Notification> notificationList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        notificationService = ApiClient.getInstance().create(NotificationService.class);

        setupNotificationList();
        loadNotifications(0, 20);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotifications(0, 20);
    }

    private void setupNotificationList() {
        adapter = new NotificationAdapter(notificationList);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setRemoveDuration(300); // thời gian xóa
        binding.notificationList.setItemAnimator(animator);

        binding.notificationList.setHasFixedSize(true);
        binding.notificationList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.notificationList.setAdapter(adapter);
    }

    private void loadNotifications(int page, int size) {
        String token = "Bearer " + SharedPrefsManager.getInstance(requireContext()).getToken();

        notificationService.getMyNotifications(page, size, token)
                .enqueue(new Callback<PageNotificationResponse>() {
                    @Override
                    public void onResponse(Call<PageNotificationResponse> call, Response<PageNotificationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear danh sách cũ
                            notificationList.clear();

                            // Convert
                            for (NotificationResponse res : response.body().getContent()) {
                                Notification noti = new Notification(
                                        res.getNotificationId(),
                                        res.getSenderAvt(),
                                        res.getSenderName(),
                                        res.getReceiverId(),
                                        res.getReceiverFullName(),
                                        res.getType(),
                                        res.getContent(),
                                        res.getIsRead(),
                                        res.getTimestamp(),
                                        res.getActionUrl()
                                );
                                notificationList.add(noti);
                            }

                            adapter.notifyDataSetChanged(); // Cập nhật UI
                        } else {
                            Toast.makeText(getContext(), "Không tải được thông báo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PageNotificationResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}