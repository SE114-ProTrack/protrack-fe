package com.example.protrack.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.protrack.R;
import com.example.protrack.model.Member;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {

    private final List<Member> memberList;

    public MemberAdapter(Context context, List<Member> members) {
        super(context, 0, members);
        this.memberList = members;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Member member = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.component_member_list_item, parent, false);
        }

        // Ánh xạ view
        TextView txtName = convertView.findViewById(R.id.txt_name);
        TextView txtRole = convertView.findViewById(R.id.txt_role);
        TextView txtOnline = convertView.findViewById(R.id.txt_online);
        ImageView imgAvatar = convertView.findViewById(R.id.img_avatar);
        ImageView imgCheck = convertView.findViewById(R.id.img_check);
        View dotOnline = convertView.findViewById(R.id.dot_online);
        ConstraintLayout layout = convertView.findViewById(R.id.member_container);
        // Set giá trị
        txtName.setText(member.getName());
        txtRole.setText(member.getRole());
        txtOnline.setText(member.getStatusText());
        // Ẩn trạng thái nếu được chọn
        txtOnline.setVisibility(member.isSelected() ? View.INVISIBLE : View.VISIBLE);

        // Hiển thị tick nếu đã chọn
        imgCheck.setVisibility(member.isSelected() ? View.VISIBLE : View.INVISIBLE);

        // Hiển thị dot nếu online
        dotOnline.setVisibility(
                member.getStatusText().toLowerCase().contains("online") ?
                        View.VISIBLE : View.INVISIBLE
        );
        // Xử lý click vào layout → toggle chọn
        if (member.isSelected()) {
            layout.setBackgroundResource(R.drawable.bg_outlined_18);
        } else {
            layout.setBackgroundResource(R.drawable.bg_transparent);
        }

 // Set avatar nếu bạn có ảnh từ URL hoặc drawable
        // Ví dụ nếu ảnh là resource nội bộ:
        // imgAvatar.setImageResource(member.getAvatarResId());

        return convertView;
    }
}
