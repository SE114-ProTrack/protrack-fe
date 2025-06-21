package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.databinding.ActivityTaskCardBinding;
import com.example.protrack.model.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private final OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TaskListAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ActivityTaskCardBinding binding;

        public TaskViewHolder(@NonNull ActivityTaskCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Task task) {

            binding.tvTitle.setText(task.getTitle());


            binding.getRoot().setOnClickListener(v -> listener.onTaskClick(task));
        }
    }

    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActivityTaskCardBinding binding = ActivityTaskCardBinding.inflate(inflater, parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }
}
