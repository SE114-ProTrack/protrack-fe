package com.example.protrack.ui.activities;
import com.example.protrack.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.protrack.model.Comment;

import java.util.List;

public class CommentCardAdapter extends BaseAdapter {
    private Activity context;
    private List<Comment> commentList;
    private int visibleItemCount;

    public CommentCardAdapter(Activity context, List<Comment> commentList, int visibleItemCount) {
        this.context = context;
        this.commentList = commentList;
        this.visibleItemCount = visibleItemCount;
    }
    public void increaseVisibleItemCount(int amount) {
        visibleItemCount = Math.min(visibleItemCount + amount, commentList.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Math.min(visibleItemCount, commentList.size());
    }
    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_comment_card, parent, false);
        }

        Comment comment = commentList.get(position);

        TextView name = view.findViewById(R.id.tv_name);
        TextView time = view.findViewById(R.id.tv_time);
        TextView content = view.findViewById(R.id.comment_text);
        ImageView avatar = view.findViewById(R.id.avatar); // Có thể thay ảnh nếu cần

        name.setText(comment.getName());
        time.setText(comment.getTime());
        content.setText(comment.getContent());

        return view;
    }
}
