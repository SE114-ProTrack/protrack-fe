package com.example.protrack.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.protrack.R;
import com.example.protrack.databinding.FragmentProjectMemberListBinding;
import com.example.protrack.model.Member;
import com.example.protrack.ui.adapters.ProjectMemberListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectMemberListFragment extends Fragment {

    private FragmentProjectMemberListBinding projectMemberListBinding;
    private ProjectMemberListAdapter projectMemberAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        projectMemberListBinding = FragmentProjectMemberListBinding.inflate(inflater, container, false);
        return projectMemberListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupMemberList();
        loadMockMembers();

    }

    private void setupMemberList() {
        projectMemberAdapter = new ProjectMemberListAdapter(new ArrayList<>(), member -> {
            // TODO: Xử lý khi click vào một member
//            Intent intent = new Intent(requireContext(), TaskDetailActivity.class);
//            startActivity(intent);
        });

        projectMemberListBinding.getRoot().setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        projectMemberListBinding.memberList.setAdapter(projectMemberAdapter);
    }

    private void loadMockMembers() {
        List<Member> mockList = new ArrayList<>();

        mockList.add(new Member(
                UUID.randomUUID().toString(), // id
                "Tien Tom",                   // name
                "UI / UX Designer",           // role
                "Online",                     // status
                R.drawable.ic_user,           // avatar
                false                         // selected
        ));

        mockList.add(new Member(
                UUID.randomUUID().toString(),
                "Tom Tien",
                "Leader",
                "Offline 2h ago",
                R.drawable.ic_user,
                false
        ));
        projectMemberAdapter.setMembers(mockList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        projectMemberListBinding = null;
    }
}