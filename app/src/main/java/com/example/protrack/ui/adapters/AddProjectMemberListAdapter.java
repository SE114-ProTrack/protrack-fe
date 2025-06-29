package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.databinding.ComponentAddProjectMemberListItemBinding;
import com.example.protrack.model.UserProfile;

import java.util.List;

public class AddProjectMemberListAdapter extends RecyclerView.Adapter<AddProjectMemberListAdapter.AddProjectMemberViewHolder> {

    private List<UserProfile> userList;
    private final OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onUserClick(UserProfile user);
    }

    public AddProjectMemberListAdapter(List<UserProfile> userList, OnDeleteButtonClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    class AddProjectMemberViewHolder extends RecyclerView.ViewHolder {
        private final ComponentAddProjectMemberListItemBinding binding;

        public AddProjectMemberViewHolder(@NonNull ComponentAddProjectMemberListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserProfile user) {

            binding.userName.setText(user.getHoTen());
            binding.userEmail.setText(user.getEmail());

            binding.deleteButton.setOnClickListener(v -> listener.onUserClick(user));
        }
    }

    @NonNull
    @Override
    public AddProjectMemberListAdapter.AddProjectMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentAddProjectMemberListItemBinding binding = ComponentAddProjectMemberListItemBinding.inflate(inflater, parent, false);
        return new AddProjectMemberListAdapter.AddProjectMemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProjectMemberListAdapter.AddProjectMemberViewHolder holder, int position) {
        UserProfile user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }
}
