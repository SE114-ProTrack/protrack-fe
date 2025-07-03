package com.example.protrack.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Member;
import com.example.protrack.ui.adapters.MemberAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddMemberActivity extends AppCompatActivity {

    private RecyclerView memberRecycleView;
    private TextView txtSelectedCount;
    private List<Member> members;
    private MemberAdapter adapter;
    private ImageButton addMemberBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        memberRecycleView = findViewById(R.id.member_recycleview);
        txtSelectedCount = findViewById(R.id.txtSelectedCount);
        addMemberBtn= findViewById(R.id.addMemberBtn);
        members = new ArrayList<>();
        members.add(new Member("1", "Alice", "Designer", "Online", R.drawable.ic_user, false));
        members.add(new Member("2", "Bob", "Dev", "Offline 2h ago", R.drawable.ic_user, false));

        adapter = new MemberAdapter(this, members);
        memberRecycleView.setLayoutManager(new LinearLayoutManager(this)); // <== BẮT BUỘC CHO RECYCLER VIEW
        memberRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Member m = members.get(position);
            m.setSelected(!m.isSelected());
            adapter.notifyItemChanged(position);

            long count = members.stream().filter(Member::isSelected).count();
            txtSelectedCount.setText("Selected (" + count + ")");
        });
        addMemberBtn.setOnClickListener(v -> {
            ArrayList<Member> selectedMembers = new ArrayList<>();
            for (Member m : members) {
                if (m.isSelected()) {
                    selectedMembers.add(m);
                }
            }

            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("selectedMembers", selectedMembers);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
        // Nút quay về
        findViewById(R.id.backButton).setOnClickListener(v -> finish());


    }
}