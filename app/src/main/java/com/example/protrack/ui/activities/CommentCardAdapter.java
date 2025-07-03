package com.example.protrack.ui.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Comment;

import java.util.List;

public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardAdapter.CommentViewHolder> {
    private final Context context;
    private final List<Comment> commentList;
    private int visibleCount;

    public CommentCardAdapter(Context context, List<Comment> commentList, int visibleCount) {
        this.context = context;
        this.commentList = commentList;
        this.visibleCount = Math.min(visibleCount, commentList.size());
    }

    public void increaseVisibleItemCount(int count) {
        visibleCount = Math.min(visibleCount + count, commentList.size());
        notifyDataSetChanged();
    }

    public void setVisibleCount(int count) {
        visibleCount = Math.min(count, commentList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comment_card, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return Math.min(visibleCount, commentList.size());
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView contentText, name, time;
        ImageView avatar;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            contentText = itemView.findViewById(R.id.comment_text);
            name = itemView.findViewById(R.id.comment_name);
            time = itemView.findViewById(R.id.comment_time);
            avatar = itemView.findViewById(R.id.avatar);
        }

        public void bind(Comment comment) {
            contentText.setText(comment.getContent());
            name.setText(comment.getName());
            time.setText(comment.getTime());
            avatar.setImageResource(comment.getAvatarResId());
        }
    }
}
