package com.example.protrack.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.protrack.R;
import com.example.protrack.model.Member;
import com.example.protrack.model.Project;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
public class CreateProjectActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton addImageBtn;
    private ImageView selectedImage;
    private ImageButton addMemberBtn;
    private ActivityResultLauncher<Intent> memberPickerLauncher;
    private LinearLayout flowAssign;
    private ImageView calendarView;
private ImageButton backBtn;
    private EditText projectNameEditText, projectDescEditText;
    private Button saveButton;
    private TextView txtFullDate;
private TextView txtDayNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        addImageBtn = findViewById(R.id.addImageBtn);
        selectedImage = findViewById(R.id.selectedImage);
        addMemberBtn = findViewById(R.id.add_member_btn);
        flowAssign = findViewById(R.id.flow_assign);
        calendarView = findViewById(R.id.calendarView);
        projectNameEditText = findViewById(R.id.projectNameEditText);
        projectDescEditText = findViewById(R.id.projectDescEditText);
        saveButton = findViewById(R.id.btn_save_project);
        backBtn = findViewById(R.id.backButton);
         txtFullDate = findViewById(R.id.fullDateText);
         txtDayNumber = findViewById(R.id.dayNumberText);
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng tính từ 0
        int year = calendar.get(Calendar.YEAR);

        // Định dạng chuỗi
        String fullDateStr = String.format("%02d/%02d/%04d", day, month, year); // dạng "03/07/2025"
        String dayStr = String.format("%02d", day); // "03"

        // Gán vào TextView
        txtFullDate.setText(fullDateStr);
        txtDayNumber.setText(dayStr);

        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish(); // Quay lại màn trước đó
        });

        // Gắn sự kiện hiển thị menu popup chọn ngày cho task
        calendarView.setOnClickListener(this::showCalendarMenu);

        memberPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra("selectedMembers")) {
                            ArrayList<Member> selectedMembers = data.getParcelableArrayListExtra("selectedMembers");
                            if (selectedMembers != null && !selectedMembers.isEmpty()) {
                                updateMemberAvatars(selectedMembers);
                            }
                        }
                    }
                }
        );

        addMemberBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMemberActivity.class);
            memberPickerLauncher.launch(intent);
        });
        // Nhấn nút +
        addImageBtn.setOnClickListener(v -> openGallery());

        // Nhấn vào ảnh đã chọn để đổi ảnh
        selectedImage.setOnClickListener(v -> openGallery());
        // Nhấn nút tạo project
        saveButton.setOnClickListener(v -> {
//            String projectName = projectNameEditText.getText().toString();
//            String projectDesc = projectDescEditText.getText().toString();
//            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
//
//            Long id = System.currentTimeMillis(); // tạo ID tạm thời bằng timestamp
//            Long userId = 1L; // Giả sử người tạo là user ID 1
//
//            Project newProject = new Project(id, projectName, projectDesc, date, userId);
//
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra("newProject", newProject);  // Project implements Serializable
//            setResult(RESULT_OK, resultIntent);
//            finish();
        });


    }
    private void showCalendarMenu(View anchor) {
        View menuView = LayoutInflater.from(this).inflate(R.layout.menu_calendar, null);
        PopupWindow popupWindow = new PopupWindow(menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setElevation(10);
        popupWindow.showAtLocation(anchor.getRootView(), android.view.Gravity.CENTER, 0, 0);
        // Lấy reference đến MaterialCalendarView trong menu
        CalendarView calendarView = menuView.findViewById(R.id.calendarView);

        // Khi người dùng chọn ngày
        calendarView.setOnDateChangeListener((CalendarView view, int year, int month, int dayOfMonth) -> {
            // Sử dụng trực tiếp:
            int day = dayOfMonth;
            int realMonth = month + 1; // vì month bắt đầu từ 0
            int fullYear = year;

            String dayStr = String.format("%02d", day); // "01"
            String fullDateStr = String.format("%02d/%02d/%04d", day, realMonth, fullYear); // "01/07/2025"

//            TextView txtDayNumber = findViewById(R.id.dayNumberText);
//            TextView txtFullDate = findViewById(R.id.fullDateText);

            txtDayNumber.setText(dayStr);
            txtFullDate.setText(fullDateStr);
            popupWindow.dismiss();
        });
    }
    private void updateMemberAvatars(List<Member> members) {
        if (members == null || members.isEmpty()) return;

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Member m : members) {
            // Kiểm tra nếu member đã được thêm thì bỏ qua (tránh trùng)
            boolean alreadyAdded = false;
            for (int i = 0; i < flowAssign.getChildCount(); i++) {
                View v = flowAssign.getChildAt(i);
                Object tag = v.getTag();
                if (tag instanceof String && tag.equals(m.getName())) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (alreadyAdded) continue;

            // Inflate layout avatar
            View view = inflater.inflate(R.layout.component_avatar_member, flowAssign, false);
            ImageView avatar = view.findViewById(R.id.member_avatar);
            ImageButton removeBtn = view.findViewById(R.id.removeMemberBtn);

            avatar.setImageResource(m.getAvatarResId());
            view.setTag(m.getName()); // dùng tag để kiểm tra trùng

            removeBtn.setOnClickListener(v -> flowAssign.removeView(view));

            // Thêm vào trước nút cộng cuối cùng
            int insertPosition = Math.max(flowAssign.getChildCount() - 1, 0);
            flowAssign.addView(view, insertPosition);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImage.setImageURI(imageUri);

            // Ẩn nút +, hiển thị ảnh
            addImageBtn.setVisibility(View.GONE);
            selectedImage.setVisibility(View.VISIBLE);
        }
    }
}
