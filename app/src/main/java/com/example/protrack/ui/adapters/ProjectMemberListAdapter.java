package com.example.protrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.databinding.ComponentProjectMemberListItemBinding;
import com.example.protrack.model.Member;

import java.util.List;

public class ProjectMemberListAdapter extends RecyclerView.Adapter<ProjectMemberListAdapter.ProjectMemberViewHolder> {

    private List<Member> memberList;
    private final OnMemberClickListener listener;

    public interface OnMemberClickListener {
        void onMemberClick(Member member);
    }

    public ProjectMemberListAdapter(List<Member> memberList, OnMemberClickListener listener) {
        this.memberList = memberList;
        this.listener = listener;
    }

    public class ProjectMemberViewHolder extends RecyclerView.ViewHolder {
        private final ComponentProjectMemberListItemBinding binding;

        public ProjectMemberViewHolder(@NonNull ComponentProjectMemberListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Member member) {

            binding.memberName.setText(member.getName());
            binding.memberRole.setText(member.getRole());

            binding.getRoot().setOnClickListener(v -> listener.onMemberClick(member));
        }
    }

    public void setMembers(List<Member> members) {
        this.memberList = members;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentProjectMemberListItemBinding binding = ComponentProjectMemberListItemBinding.inflate(inflater, parent, false);
        return new ProjectMemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectMemberViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.bind(member);
    }

    @Override
    public int getItemCount() {
        return memberList == null ? 0 : memberList.size();
    }
}
