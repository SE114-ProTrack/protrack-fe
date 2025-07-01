package com.example.protrack.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Member;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private final Context context;
    private final List<Member> memberList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MemberAdapter(Context context, List<Member> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.component_member_list_item, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = memberList.get(position);

        holder.txtName.setText(member.getName());
        holder.txtRole.setText(member.getRole());
        holder.txtOnline.setText(member.getStatusText());

        holder.txtOnline.setVisibility(member.isSelected() ? View.INVISIBLE : View.VISIBLE);
        holder.imgCheck.setVisibility(member.isSelected() ? View.VISIBLE : View.INVISIBLE);
        holder.dotOnline.setVisibility(member.getStatusText().toLowerCase().contains("online")
                ? View.VISIBLE : View.INVISIBLE);

        holder.layout.setBackgroundResource(member.isSelected()
                ? R.drawable.bg_outlined_18 : R.drawable.bg_transparent);

        holder.imgAvatar.setImageResource(member.getAvatarResId()); // nếu có ảnh từ resource

        // 👇 Xử lý click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtOnline;
        ImageView imgAvatar, imgCheck;
        View dotOnline;
        ConstraintLayout layout;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtRole = itemView.findViewById(R.id.txt_role);
            txtOnline = itemView.findViewById(R.id.txt_online);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            imgCheck = itemView.findViewById(R.id.img_check);
            dotOnline = itemView.findViewById(R.id.dot_online);
            layout = itemView.findViewById(R.id.member_container);
        }
    }
}
