package com.example.protrack.ui.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.protrack.R;
import com.example.protrack.model.Member;
import com.example.protrack.ui.adapters.MemberAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddMemberActivity extends AppCompatActivity {

    private ListView listView;
    private TextView txtSelectedCount;
    private List<Member> members;
    private MemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        listView = findViewById(R.id.listview);
        txtSelectedCount = findViewById(R.id.txtSelectedCount);

        members = new ArrayList<>();
        members.add(new Member("Alice", "Designer", "Online", R.drawable.ic_user, false));
        members.add(new Member("Bob", "Dev", "Offline 2h ago", R.drawable.ic_user, false));

        adapter = new MemberAdapter(this, members);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Member m = members.get(position);
            m.setSelected(!m.isSelected());
            adapter.notifyDataSetChanged();

            long count = members.stream().filter(Member::isSelected).count();
            txtSelectedCount.setText("Selected (" + count + ")");
        });


    }
}