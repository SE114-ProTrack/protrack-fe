package com.example.protrack.ui.adapters;
import android.graphics.Typeface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.ChatSummary;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private final Context context;
    private final List<ChatSummary> chatSummaries;
    private final OnItemClickListener onItemClickListener;
    // Interface callback để truyền sự kiện click
    public interface OnItemClickListener {
        void onItemClick(ChatSummary item);
    }

    // Constructor có callback
    public ChatListAdapter(Context context, List<ChatSummary> chatSummaries, OnItemClickListener listener) {
        this.context = context;
        this.chatSummaries = chatSummaries;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_chat_list_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatSummary item = chatSummaries.get(position);
        holder.nameTextView.setText(item.getSenderName());
        holder.messageTextView.setText(item.getLastMessage());
        holder.timeTextView.setText(item.getTimeAgo());
        // Hiển thị badge số tin chưa đọc
        if (item.getUnreadCount() > 0) {
            holder.unreadBadge.setVisibility(View.VISIBLE);
            holder.unreadBadge.setText(String.valueOf(item.getUnreadCount()));
            //  In đậm nếu còn tin chưa đọc
            holder.nameTextView.setTypeface(null, Typeface.BOLD);
            holder.messageTextView.setTypeface(null, Typeface.BOLD);
        } else {
            holder.unreadBadge.setVisibility(View.GONE);
            //  Bình thường nếu đã đọc
            holder.nameTextView.setTypeface(null, Typeface.NORMAL);
            holder.messageTextView.setTypeface(null, Typeface.NORMAL);
        }

        // TODO: Load ảnh avatar nếu có (hiện bạn đang dùng drawable cố định)
        holder.avatarImage.setImageResource(R.drawable.ic_user); // placeholder
        // Dùng thư viện Glide hoặc Picasso để load ảnh từ URL như sau:

    /*
    Glide.with(context)
         .load(item.getAvatarUrl()) // avatar từ URL
         .placeholder(R.drawable.ic_user) // ảnh tạm trong khi chờ tải
         .circleCrop() // bo tròn nếu muốn
         .into(holder.avatarImage);
    */

        //  Ghi chú: Đừng quên thêm thư viện Glide vào build.gradle:
        // implementation 'com.github.bumptech.glide:glide:4.16.0'
        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        });

    }


    @Override
    public int getItemCount() {
        return chatSummaries.size();
    }

    // ViewHolder giữ tham chiếu đến các View trong layout để tái sử dụng
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView nameTextView, messageTextView, timeTextView, unreadBadge;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.img_avatar);
            nameTextView = itemView.findViewById(R.id.member_name_textView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timetextView);
            unreadBadge = itemView.findViewById(R.id.unreadBadge);
        }
    }
}
