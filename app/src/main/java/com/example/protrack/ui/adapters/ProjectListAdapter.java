package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.databinding.ComponentProjectListItemBinding;
import com.example.protrack.model.Project;
import com.example.protrack.utils.Utils;

import java.util.List;

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

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final ComponentProjectListItemBinding binding;

        public ProjectViewHolder(@NonNull ComponentProjectListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Project project) {
            binding.projectName.setText(project.getTenDuAn());

            binding.description.setText(Utils.limitString(project.getMoTa(), 21)
                    + " - " + project.getThoiGianTao());

            //binding.dayLeft.setText(project.getDate());
            //binding.progressBar.setProgress(project.getProgress());

            // TODO: Load image or icon if needed
            // Example: Glide.with(binding.avatar.getContext()).load(project.getImageUrl()).into(binding.avatar);

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
