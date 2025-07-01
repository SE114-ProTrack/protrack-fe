package com.example.protrack.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CalendarView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.content.res.ColorStateList;
import android.widget.TextView;
import com.google.android.material.checkbox.MaterialCheckBox;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;

import com.example.protrack.R;
import com.example.protrack.model.Member;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.skydoves.colorpickerview.AlphaTileView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {
    private MaterialButton btnNotStarted, btnInProgress, btnCompleted;
    private ImageView icTask;
    private ImageView calendarView;
    private ImageButton addMemberBtn;
    private ImageButton addFileBtn;
    private ChipGroup flowAssign;
    private ImageButton addLabelBtn;// trong activity_edit_task
    private ImageButton btnAddLabel;// trong menu_add_label
    private EditText editText;// trong menu_add_label
    private ColorPickerView colorPickerView;
    private AlphaTileView flagColorLayout;
    private EditText flagColorCode;
    private ChipGroup flow1;// dành cho add label trong activity_edit_task
    private ChipGroup flow2;// dành cho add file  trong activity_edit_task

    private ActivityResultLauncher<Intent> memberPickerLauncher;
    ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        String fileName = getFileNameFromUri(uri);
                        addFileChip(fileName);
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Khởi tạo các view
        btnNotStarted = findViewById(R.id.btnNotStarted);
        btnInProgress = findViewById(R.id.btnInProgress);
        btnCompleted = findViewById(R.id.btnCompleted);
        icTask = findViewById(R.id.icon_task);
        calendarView = findViewById(R.id.calendarView);
        addLabelBtn = findViewById(R.id.addLabelBtn);
        addMemberBtn = findViewById(R.id.add_member_btn);
        flowAssign = findViewById(R.id.flow_assign);
        flow1 = findViewById(R.id.flow1);

//
        List<MaterialButton> buttons = Arrays.asList(btnNotStarted, btnInProgress, btnCompleted);



        for (MaterialButton button : buttons) {
            button.setOnClickListener(v -> {
                for (MaterialButton b : buttons) {
                    b.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                    b.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#26495C")));
                    b.setTextColor(Color.parseColor("#26495C"));
                    b.setTag(null);
                }
                MaterialButton selected = (MaterialButton) v;
                selected.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#26495C")));
                selected.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT));
                selected.setTextColor(Color.WHITE);
                selected.setTag("selected");
            });
        }
        // Nút quay về
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        // Gắn sự kiện hiển thị menu popup thêm thành viên cho task
        icTask.setOnClickListener(this::showTaskIconMenu);
        // Gắn sự kiện hiển thị menu popup chọn ngày cho task
        calendarView.setOnClickListener(this::showCalendarMenu);
        // Gắn sự kiện hiển thị menu popup chọn màu và nhập tên cho label
        addLabelBtn.setOnClickListener(this::showAddLabelMenu);
        addMemberBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMemberActivity.class);
            memberPickerLauncher.launch(intent);
        });
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

        flow2 = findViewById(R.id.flow2);
        addFileBtn = findViewById(R.id.addFileBtn);

        addFileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            filePickerLauncher.launch(Intent.createChooser(intent, "Select File"));
        });
    }
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            // fallback nếu không đọc được tên từ OpenableColumns
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void addFileChip(String fileName) {
        View fileChip = LayoutInflater.from(this).inflate(R.layout.component_file, flow2, false);
        MaterialButton button = fileChip.findViewById(R.id.btnAttachment);
        button.setText(fileName);
        flow2.addView(button, flow2.getChildCount() - 1); // Thêm vào trước addFileBtn
    }

    private void addLabelToLayout(String labelText, int color) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View labelView = inflater.inflate(R.layout.component_label, flow1, false);

        LinearLayout leftHalf = labelView.findViewById(R.id.left_half);
        LinearLayout rightHalf = labelView.findViewById(R.id.right_half);
        TextView labelTextView = labelView.findViewById(R.id.label_text);
        MaterialCheckBox checkBox = labelView.findViewById(R.id.checkbox); // Nếu bạn dùng CheckBox

        // Set background cho nửa trái là mặc định (xám nhạt), không đổi
        leftHalf.setBackgroundResource(R.drawable.bg_left_half); // bo góc trái

        // Tint nửa phải theo màu được truyền vào
        Drawable rightBg = ContextCompat.getDrawable(this, R.drawable.bg_right_half).mutate();
        DrawableCompat.setTint(rightBg, color);
        rightHalf.setBackground(rightBg);

        labelTextView.setText(labelText);

        //  Tạo ColorStateList để đổi màu dấu tick khi được chọn
        ColorStateList tintList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},   // checked
                        new int[]{}                                // unchecked
                },
                new int[]{
                        ContextCompat.getColor(this, R.color.colorPrimary), // màu khi checked
                        Color.TRANSPARENT                                   // không có màu
                }
        );
        // Áp dụng cho nút tick
        CompoundButtonCompat.setButtonTintList(checkBox, tintList);

        flow1.addView(labelView, flow1.getChildCount() - 1);
    }



    private void showAddLabelMenu(View anchor) {
        View menuView = LayoutInflater.from(this).inflate(R.layout.menu_add_label, null);

        PopupWindow popupWindow = new PopupWindow(menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setElevation(10);
        popupWindow.showAtLocation(anchor.getRootView(), Gravity.CENTER, 0, 0);

        // GÁN LẠI BIẾN TOÀN CỤC TỪ menuView (không dùng findViewById trực tiếp nữa)
        colorPickerView = menuView.findViewById(R.id.colorPickerView);
        flagColorLayout = menuView.findViewById(R.id.flag_color_layout);
        flagColorCode = menuView.findViewById(R.id.flag_color_code);

         btnAddLabel = menuView.findViewById(R.id.btnAddLabel);
         editText = menuView.findViewById(R.id.editTextLabelName); // ID của ô nhập label bạn đặt tên gì thì thay vào
        final int[] selectedColor = {Color.parseColor("#26495C")}; // mặc định nếu chưa chọn gì


        // PHẢI đặt setColorListener SAU KHI GÁN BIẾN
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                int color = envelope.getColor();
                String hexCode = envelope.getHexCode();
                flagColorLayout.setBackgroundColor(color);
                flagColorCode.setText("#" + hexCode);
                selectedColor[0] = color; // lưu lại để dùng khi nhấn nút Add
            }
        });

        btnAddLabel.setOnClickListener(v -> {
            String labelName = editText.getText().toString().trim();
            if (!labelName.isEmpty()) {
                addLabelToLayout(labelName, selectedColor[0]);
                popupWindow.dismiss();
            }
        });


    }



    private void displaySelectedMembers(List<Member> members) {
        for (Member m : members) {
            ImageView avatar = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
            params.setMarginEnd(8);
            avatar.setLayoutParams(params);
            avatar.setScaleType(ImageView.ScaleType.FIT_CENTER);
            avatar.setImageResource(m.getAvatarResId()); // cần có getter
            avatar.setBackgroundResource(R.drawable.bg_avatar_circle);
            flowAssign.addView(avatar);
        }
        // Add lại nút "+"
        View addBtn = LayoutInflater.from(this).inflate(R.layout.component_dashed_add_button, flowAssign, false);
        flowAssign.addView(addBtn);
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

            avatar.setImageResource(m.getAvatarUrl());
            view.setTag(m.getName()); // dùng tag để kiểm tra trùng

            removeBtn.setOnClickListener(v -> flowAssign.removeView(view));

            // Thêm vào trước nút cộng cuối cùng
            int insertPosition = Math.max(flowAssign.getChildCount() - 1, 0);
            flowAssign.addView(view, insertPosition);
        }
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

            TextView txtDayNumber = findViewById(R.id.dayNumberText);
            TextView txtFullDate = findViewById(R.id.fullDateText);

            txtDayNumber.setText(dayStr);
            txtFullDate.setText(fullDateStr);
            popupWindow.dismiss();
        });
    }

    private void showTaskIconMenu(View anchor) {
        View menuView = LayoutInflater.from(this).inflate(R.layout.menu_task_icon, null);
        PopupWindow popupWindow = new PopupWindow(menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setElevation(10);

        // Vị trí hiển thị: bạn có thể điều chỉnh nếu muốn popup lệch phải/trái
        popupWindow.showAsDropDown(anchor, 100, -60);
        // Gắn sự kiện cho các thành phần trong menu
//        menuView.findViewById(R.id.taskIcon1).setOnClickListener(v -> {
//            // TODO: xử lý chọn icon 1
//            popupWindow.dismiss();
//        });
//        menuView.findViewById(R.id.taskIcon2).setOnClickListener(v -> {
//            // TODO: xử lý chọn icon 2
//            popupWindow.dismiss();
//        });

    }
}
