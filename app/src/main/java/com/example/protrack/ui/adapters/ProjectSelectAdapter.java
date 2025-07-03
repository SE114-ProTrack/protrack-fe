package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.databinding.ComponentSelectProjectListItemBinding;
import com.example.protrack.model.Project;

import java.util.List;

public class ProjectSelectAdapter extends RecyclerView.Adapter<ProjectSelectAdapter.ProjectViewHolder> {

    private List<Project> projectList;
    private final OnProjectClickListener listener;

    // Interface để xử lý click
    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public ProjectSelectAdapter(List<Project> projectList, OnProjectClickListener listener) {
        this.projectList = projectList;
        this.listener = listener;
    }

    public void setProjects(List<Project> projectList) {
        this.projectList = projectList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentSelectProjectListItemBinding binding =
                ComponentSelectProjectListItemBinding.inflate(inflater, parent, false);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        holder.bind(projectList.get(position));
    }

    @Override
    public int getItemCount() {
        return projectList == null ? 0 : projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final ComponentSelectProjectListItemBinding binding;

        public ProjectViewHolder(@NonNull ComponentSelectProjectListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Project project) {
            binding.projectName.setText(project.getTenDuAn());
            binding.memberRole.setText(project.getMoTa());

            binding.getRoot().setOnClickListener(v -> listener.onProjectClick(project));
        }
    }
}
