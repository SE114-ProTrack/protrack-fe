package com.example.protrack.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.protrack.R;
import com.example.protrack.model.Comment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_comment_card, parent, false);
        }

        Comment comment = getItem(position);
        if (comment != null) {
            ImageView avatar = convertView.findViewById(R.id.avatar);
            TextView commentText = convertView.findViewById(R.id.comment_text);

            avatar.setImageResource(comment.getAvatarResId());
            commentText.setText(comment.getContent());
        }

        return convertView;
    }
}

