package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.protrack.databinding.ComponentProjectListItemBinding;
import com.example.protrack.model.Project;
import com.example.protrack.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private List<Project> projectList;
    private final OnProjectClickListener listener;

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public ProjectListAdapter(List<Project> projectList, OnProjectClickListener listener) {
        this.projectList = projectList;
        this.listener = listener;
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final ComponentProjectListItemBinding binding;

        public ProjectViewHolder(@NonNull ComponentProjectListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Project project) {
            binding.projectName.setText(project.getTenDuAn());
            binding.description.setText(Utils.limitString(project.getMoTa(), 36));
            binding.completedTask.setText(String.valueOf(project.getTaskHoanThanh()));
            binding.totalTask.setText("/" + project.getTongTask() + " task");
            binding.progressBar.setProgress((int)((float)project.getTaskHoanThanh() / project.getTongTask() * 100));
            binding.taskLeft.setText(project.getTongTask() - project.getTaskHoanThanh() + " task left");

            Glide.with(binding.projectBanner.getContext())
                    .load(project.getAnhBiaDuAn())
                    .into(binding.projectBanner);

            binding.getRoot().setOnClickListener(v -> listener.onProjectClick(project));
        }
    }

    public void setProjects(List<Project> projects) {
        this.projectList = projects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentProjectListItemBinding binding = ComponentProjectListItemBinding.inflate(inflater, parent, false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return projectList == null ? 0 : projectList.size();
    }


}
