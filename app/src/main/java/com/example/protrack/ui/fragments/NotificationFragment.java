package com.example.protrack.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.protrack.databinding.FragmentNotificationBinding;
import com.example.protrack.model.Notification;
import com.example.protrack.ui.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private NotificationAdapter adapter;
    private final List<Notification> notificationList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadMockNotifications();
        setupNotificationList();

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

    private void loadMockNotifications() {
        notificationList.add(new Notification("1", "Design", "Tien Tom", "Add new attachment"));
        notificationList.add(new Notification("2", "Design", "Tien Tom", "Leave a comment"));
        notificationList.add(new Notification("3", "Design", "Tien Tom", "Have been invited to the project"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}