package com.example.protrack.ui.activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.BaseAdapter;

import com.example.protrack.R;

import java.util.List;

public class TaskCardAdapter extends BaseAdapter {

    private Activity context;
    private List<Task> taskList;

    public TaskCardAdapter(Activity context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_task_card, parent, false);
        }

        Task task = taskList.get(position);

        // Gán dữ liệu cho các thành phần trong layout
        TextView title = view.findViewById(R.id.tv_title);
        TextView category = view.findViewById(R.id.tag_design);
        TextView code = view.findViewById(R.id.tag_code);

        title.setText(task.getTitle());
        category.setText(task.getCategory());
        code.setText(task.getCode());

        return view;
    }
}
