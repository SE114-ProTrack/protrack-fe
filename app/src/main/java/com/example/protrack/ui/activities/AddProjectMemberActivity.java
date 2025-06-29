package com.example.protrack.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.databinding.ActivityAddProjectMemberBinding;
import com.example.protrack.model.UserProfile;
import com.example.protrack.ui.adapters.AddProjectMemberListAdapter;
import com.example.protrack.ui.adapters.SearchUserListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddProjectMemberActivity extends AppCompatActivity {

    ActivityAddProjectMemberBinding binding;

    AddProjectMemberListAdapter memberListAdapter;

    private final List<UserProfile> selectedUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
        setupMemberList();
    }

    private void setupListeners() {
        // quay về
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.saveButton.setOnClickListener(v -> {

        });

        List<UserProfile> allUsers = new ArrayList<>();
        allUsers.add(new UserProfile(1L, "Tien Tom", "tientom123@gmail.com", "1/1/1990", "Nữ", "0123456789", "", ""));
        allUsers.add(new UserProfile(1L, "Tom Tien", "tomtien123@gmail.com", "1/1/1990", "Nữ", "0123456789", "", ""));
        allUsers.add(new UserProfile(1L, "Tom Tien", "tomtien123@gmail.com", "1/1/1990", "Nữ", "0123456789", "", ""));
        allUsers.add(new UserProfile(1L, "Tom Tien", "tomtien123@gmail.com", "1/1/1990", "Nữ", "0123456789", "", ""));
        allUsers.add(new UserProfile(1L, "Tom Tien", "tomtien123@gmail.com", "1/1/1990", "Nữ", "0123456789", "", ""));
        allUsers.add(new UserProfile(1L, "Tom Tien", "tomtien123@gmail.com", "1/1/1990", "Nữ", "0123456789", "", ""));

        EditText searchInput = binding.searchBar.searchInput;
        searchInput.addTextChangedListener(new TextWatcher() {
            private PopupWindow popupWindow;

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase();
                if (query.isEmpty()) {
                    if (popupWindow != null) popupWindow.dismiss();
                    return;
                }

                List<UserProfile> filtered = new ArrayList<>();
                for (UserProfile user : allUsers) {
                    if (user.getHoTen().toLowerCase().contains(query)) filtered.add(user);
                }

                if (filtered.isEmpty()) {
                    if (popupWindow != null) popupWindow.dismiss();
                    return;
                }

                // Inflate layout
                View popupView = LayoutInflater.from(AddProjectMemberActivity.this)
                        .inflate(R.layout.menu_search_user_list, null);

                RecyclerView recyclerView = popupView.findViewById(R.id.userList);
                recyclerView.setLayoutManager(new LinearLayoutManager(AddProjectMemberActivity.this));
                recyclerView.setAdapter(new SearchUserListAdapter(filtered, selectedUser -> {
                    // Tránh thêm trùng
                    if (!selectedUsers.contains(selectedUser)) {
                        selectedUsers.add(selectedUser);
                        memberListAdapter.notifyItemInserted(selectedUsers.size() - 1);
                        updateSelectedCount();
                    }
                    searchInput.setText(""); // Clear search
                    popupWindow.dismiss();
                }));

                popupWindow = new PopupWindow(popupView,
                        binding.searchBar.getRoot().getWidth(),
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        true);

                popupWindow.setElevation(10);
                popupWindow.showAsDropDown(binding.searchBar.getRoot(), 0, 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void setupMemberList() {
        memberListAdapter = new AddProjectMemberListAdapter(selectedUsers, user -> {
            // TODO: xử lý khi nhấn xóa 1 user
            int index = selectedUsers.indexOf(user);
            if (index != -1) {
                selectedUsers.remove(index);
                memberListAdapter.notifyItemRemoved(index);
                updateSelectedCount();
            }
        });

        binding.userList.setLayoutManager(new LinearLayoutManager(this));
        binding.userList.setAdapter(memberListAdapter);
    }

    private void updateSelectedCount() {
        binding.txtSelectedCount.setText("Selected(" + selectedUsers.size() + ")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}