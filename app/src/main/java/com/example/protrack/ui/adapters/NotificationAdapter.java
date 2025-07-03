package com.example.protrack.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.databinding.ComponentNotificationListItemBinding;
import com.example.protrack.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public final ComponentNotificationListItemBinding binding;

        public NotificationViewHolder(@NonNull ComponentNotificationListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Notification notification) {

            binding.name.setText(notification.getSenderName());
            binding.message.setText(notification.getMessage());

            int bgResId = notification.getIsRead() ? R.drawable.bg_unread_notification : R.drawable.bg_contained_18;
            binding.getRoot().setBackgroundResource(bgResId);

            binding.deleteButton.setOnClickListener(v -> {
                int index = notificationList.indexOf(notification);
                if (index != -1) {
                    notificationList.remove(index);
                    notifyItemRemoved(index);
                }
            });
        }
    }

    public void setNotifications(List<Notification> notifications) {
        this.notificationList = notifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentNotificationListItemBinding binding = ComponentNotificationListItemBinding.inflate(inflater, parent, false);
        return new NotificationAdapter.NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
