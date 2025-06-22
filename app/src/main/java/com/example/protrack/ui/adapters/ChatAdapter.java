package com.example.protrack.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.protrack.R;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<com.example.protrack.model.Chat> messages;

    public ChatAdapter(Context context, List<com.example.protrack.model.Chat> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // 0: received, 1: sent
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).isSent() ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.protrack.model.Chat msg = messages.get(position);

        if (convertView == null) {
            int layout = msg.isSent() ? R.layout.component_sent_message : R.layout.component_received_message;
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        TextView text = convertView.findViewById(R.id.messageText);
        text.setText(msg.getContent());

        return convertView;
    }
}
