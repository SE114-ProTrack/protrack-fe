package com.example.protrack.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<Notification> notificationList;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View notiCard;
        ImageView imgAvatar;
        TextView txtName, txtStatus;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ view trong layout
            notiCard = itemView.findViewById(R.id.noti_card);
            imgAvatar = itemView.findViewById(R.id.img_user_avatar);
            txtName = itemView.findViewById(R.id.txt_name);
            txtStatus = itemView.findViewById(R.id.txt_status_noti);
            btnDelete = itemView.findViewById(R.id.btn_delete_noti);

            // Gán sự kiện click vào nút delete
            btnDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Xoá phần tử tại vị trí được bấm
                    notificationList.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item của RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.component_notification_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        // Gán dữ liệu cho item tại vị trí `position`
        Notification noti = notificationList.get(position);
        holder.imgAvatar.setImageResource(noti.getUserAvatarResId());
        holder.txtName.setText(noti.getUserName());
        holder.txtStatus.setText(noti.getStatusMessage());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
