package com.example.protrack.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.protrack.R;
import com.example.protrack.model.Chat;
import com.example.protrack.model.ChatHeader;
import com.example.protrack.model.ChatItem;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<ChatItem> items;
    public ChatAdapter(Context context, List<ChatItem> items) {
        this.context = context; this.items = items;
    }
    @Override public int getCount() { return items.size(); }
    @Override public Object getItem(int i) { return items.get(i); }
    @Override public long getItemId(int i) { return i; }
    @Override public int getViewTypeCount() { return 3; }
    @Override public int getItemViewType(int position) {
        ChatItem item = items.get(position);
        if (item.getType() == ChatItem.TYPE_HEADER) return 2;
        Chat msg = (Chat) item;
        return msg.isSent() ? 1 : 0;
    }
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ChatItem item = items.get(position);
        if (item.getType() == ChatItem.TYPE_HEADER) {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.component_message_day_header, parent, false);
            TextView tv = convertView.findViewById(R.id.headerText);
            tv.setText(((ChatHeader) item).getLabel());
            return convertView;
        } else {
            Chat msg = (Chat) item;
            int layout = msg.isSent() ? R.layout.component_sent_message : R.layout.component_received_message;
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(layout, parent, false);
            TextView text = convertView.findViewById(R.id.messageText);
            text.setText(msg.getContent());
            return convertView;
        }
    }
}


