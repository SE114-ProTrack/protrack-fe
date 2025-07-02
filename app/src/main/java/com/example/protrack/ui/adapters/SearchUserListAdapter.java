package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.databinding.ComponentSearchUserListItemBinding;
import com.example.protrack.model.UserProfile;

import java.util.List;

public class SearchUserListAdapter extends RecyclerView.Adapter<SearchUserListAdapter.SearchUserViewHolder> {

    private List<UserProfile> userList;
    private final OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(UserProfile user);
    }

    public SearchUserListAdapter(List<UserProfile> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public class SearchUserViewHolder extends RecyclerView.ViewHolder {
        private final ComponentSearchUserListItemBinding binding;

        public SearchUserViewHolder(@NonNull ComponentSearchUserListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserProfile user) {

            binding.userName.setText(user.getHoTen());
            binding.userEmail.setText(user.getEmail());

            binding.getRoot().setOnClickListener(v -> listener.onUserClick(user));
        }
    }

    @NonNull
    @Override
    public SearchUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentSearchUserListItemBinding binding = ComponentSearchUserListItemBinding.inflate(inflater, parent, false);
        return new SearchUserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserViewHolder holder, int position) {
        UserProfile user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }
}
