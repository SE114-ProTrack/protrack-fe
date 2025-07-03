package com.example.protrack.ui.activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CalendarView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.content.res.ColorStateList;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protrack.model.Project;
import com.example.protrack.model.Task;
import com.example.protrack.ui.adapters.ProjectListAdapter;
import com.example.protrack.ui.adapters.ProjectSelectAdapter;
import com.google.android.material.checkbox.MaterialCheckBox;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protrack.R;
import com.example.protrack.model.Member;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.skydoves.colorpickerview.AlphaTileView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class EditTaskActivity extends AppCompatActivity {
    private MaterialButton btnNotStarted, btnInProgress, btnCompleted;
    private ImageView icTask;
    private ImageView calendarView;
    private ImageButton addMemberBtn;
    private ImageButton addFileBtn;
    private LinearLayout flowAssign;
    private ImageButton addLabelBtn;// trong activity_edit_task
    private ImageButton btnAddLabel;// trong menu_add_label
    private EditText editText;// trong menu_add_label
    private ColorPickerView colorPickerView;
    private AlphaTileView flagColorLayout;
    private EditText flagColorCode;
    private ChipGroup flow1;// dành cho add label trong activity_edit_task
    private ChipGroup flow2;// dành cho add file  trong activity_edit_task
    private String selectedTaskIconName = "ic_checklist"; // icon mặc định
    private ImageView icon_task; // để cập nhật icon trong activity_edit_task
    private int selectedTaskColor = Color.parseColor("#000000"); // mặc định đen
    private CardView color_task; // để cập nhật color cho cardview chứa icon trong activity_edit_task
    private ImageButton seeSubTaskActivity;
    private List<Member> selectedMembers = new ArrayList<>(); // hoặc nhận từ intent
    private String selectedLabelId = null; // hoặc nhận từ intent
    private String selectedIcon = null;    // nếu có icon
    private String selectedColor = null;   // nếu có color
    private String selectedFilePath = null; // nếu có file đính kèm
    private Task currentTask;
    private EditText txtProjectName;
    private ActivityResultLauncher<Intent> memberPickerLauncher;
    private List<String> projectList = Arrays.asList(
            "ProTrack", "Project Phoenix", "UI Redesign", "Backend Refactor"
    );
    private TextView txtFullDate;
    private TextView txtDayNumber;
    ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        String fileName = getFileNameFromUri(uri);
                        addFileChip(fileName, uri);
                    }

                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // 1. Gán view
        btnNotStarted = findViewById(R.id.btnNotStarted);
        btnInProgress = findViewById(R.id.btnInProgress);
        btnCompleted = findViewById(R.id.btnCompleted);
        icTask = findViewById(R.id.icon_task);
        calendarView = findViewById(R.id.calendarView);
        addLabelBtn = findViewById(R.id.addLabelBtn);
        addMemberBtn = findViewById(R.id.add_member_btn);
        flowAssign = findViewById(R.id.flow_assign);
        flow1 = findViewById(R.id.flow1);
        flow2 = findViewById(R.id.flow2);
        addFileBtn = findViewById(R.id.addFileBtn);
        txtProjectName = findViewById(R.id.text_project_name);
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

        // nhập tên project cho txtProjectName hiển thị menu_search_project

        // 2. Lấy dữ liệu Intent
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        TextView titleView = findViewById(R.id.textViewEditText);

        // 4. Xử lý click trạng thái task
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

        // 5. Các sự kiện bổ sung
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        icTask.setOnClickListener(this::showTaskIconMenu);
        calendarView.setOnClickListener(this::showCalendarMenu);
        addLabelBtn.setOnClickListener(this::showAddLabelMenu);
        addFileBtn.setOnClickListener(v -> {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            fileIntent.setType("*/*");
            fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            filePickerLauncher.launch(Intent.createChooser(fileIntent, "Select File"));
        });

        addMemberBtn.setOnClickListener(v -> {
            Intent memberIntent = new Intent(this, AddMemberActivity.class);
            memberPickerLauncher.launch(memberIntent);
        });

        memberPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<Member> selectedMembers = result.getData().getParcelableArrayListExtra("selectedMembers");
                        if (selectedMembers != null) updateMemberAvatars(selectedMembers);
                    }
                });
        // 3. Xử lý chế độ tạo / chỉnh sửa cho button save trong màn hình chung là edit và create task
//        Task currentTask = null;
//        if ("edit".equals(mode)) {
//            currentTask = (Task) intent.getSerializableExtra("task");
//            titleView.setText("Edit Task");
//            populateForm(currentTask);
//        } else {
//            titleView.setText("Create Task");
//        }
        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            String title = ((EditText) findViewById(R.id.titleInput)).getText().toString().trim();
            String description = ((EditText) findViewById(R.id.text_description)).getText().toString().trim();
            String dateStr = ((TextView) findViewById(R.id.fullDateText)).getText().toString().trim();

            // Lấy icon và color
            String icon = "";
            String color = "";

            View iconView = findViewById(R.id.icon_task);
            if (iconView.getTag() != null) {
                icon = iconView.getTag().toString();
            }

            View colorView = findViewById(R.id.color_task);
            if (colorView.getTag() != null) {
                color = colorView.getTag().toString();  // VD: "#FF5733"
            }

            // Lấy trạng thái
            String status = null;
            if (btnNotStarted.getTag() != null && "selected".equals(btnNotStarted.getTag())) {
                status = "Not Started";
            } else if (btnInProgress.getTag() != null && "selected".equals(btnInProgress.getTag())) {
                status = "In Progress";
            } else if (btnCompleted.getTag() != null && "selected".equals(btnCompleted.getTag())) {
                status = "Completed";
            }

            if (title.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tiêu đề và ngày", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse ngày
            LocalDate dueDate;
            try {
                dueDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception e) {
                Toast.makeText(this, "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy label
            String labelId = selectedLabelId;

            // Lấy danh sách member
            List<String> assigneeIds = new ArrayList<>();
            if (selectedMembers != null) {
                for (Member m : selectedMembers) {
                    assigneeIds.add(m.getId());
                }
            }

            // File đính kèm
            String attachmentPath = selectedFilePath;
            Task.Attachment attachment = null;
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                attachment = new Task.Attachment(UUID.randomUUID().toString(), null, attachmentPath, attachmentPath, "file");  // tùy backend
            }

            // Tạo task
            Task task;
            String projectName = "My Project"; // hoặc lấy từ UI nếu có

            if ("edit".equals(mode) && currentTask != null) {
                task = new Task(
                        currentTask.getId(),
                        title,
                        description,
                        dueDate,
                        status,
                        labelId,
                        attachment,
                        assigneeIds,
                        icon,
                        color,
                        projectName
                );
            } else {
                task = new Task(
                        UUID.randomUUID().toString(),
                        title,
                        description,
                        dueDate,
                        status,
                        labelId,
                        attachment,
                        assigneeIds,
                        icon,
                        color,
                        projectName
                );
            }

// Trả kết quả về
            Intent resultIntent = new Intent();
            resultIntent.putExtra("task", task);
            setResult(RESULT_OK, resultIntent);
            finish();

        });
        txtProjectName.setOnClickListener(v -> showProjectMenu(v));
    }

    private void showProjectMenu(View anchor) {
        View menuView = LayoutInflater.from(this).inflate(R.layout.menu_select_project, null);

        PopupWindow popupWindow = new PopupWindow(menuView,
                anchor.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setElevation(10);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(anchor, 0, 0);

        RecyclerView recyclerView = menuView.findViewById(R.id.projectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Project> sampleProjects = new ArrayList<>();
        sampleProjects.add(new Project(1L, "ProTrack", "Project tracking tool", "2025-07-01", 100L));
        sampleProjects.add(new Project(2L, "WireFrame Kit", "Design wireframe templates", "2025-07-02", 100L));
        sampleProjects.add(new Project(3L, "Marketing Plan", "Q3 online ads", "2025-06-01", 100L));
        sampleProjects.add(new Project(4L, "AI Research", "Model evaluation and tuning", "2025-05-15", 100L));
        sampleProjects.add(new Project(5L, "Mobile Redesign", "New layout for Android app", "2025-04-20", 100L));
        sampleProjects.add(new Project(6L, "E-learning", "LMS content structure", "2025-03-28", 100L));
        sampleProjects.add(new Project(7L, "Recruitment System", "HR automation", "2025-02-12", 100L));
        sampleProjects.add(new Project(8L, "Data Warehouse", "ETL flow rebuild", "2025-01-07", 100L));


        ProjectSelectAdapter adapter = new ProjectSelectAdapter(sampleProjects, project -> {
            txtProjectName.setText(project.getTenDuAn());
            popupWindow.dismiss();
        });

        recyclerView.setAdapter(adapter);
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

    private void addFileChip(String fileName, Uri fileUri) {
        View fileChip = LayoutInflater.from(this).inflate(R.layout.component_file, flow2, false);
        MaterialButton button = fileChip.findViewById(R.id.btnAttachment);
        button.setText(fileName);

        // Khi click => mở file
        button.setOnClickListener(v -> {
            Intent openIntent = new Intent(Intent.ACTION_VIEW);
            openIntent.setDataAndType(fileUri, getContentResolver().getType(fileUri));
            openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(openIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Không có ứng dụng nào mở được file này", Toast.LENGTH_SHORT).show();
            }
        });

        flow2.addView(button, flow2.getChildCount() - 1); // Thêm trước nút cộng
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

            avatar.setImageResource(m.getAvatarResId());// avatar đang là int
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

//            TextView txtDayNumber = findViewById(R.id.dayNumberText);
//            TextView txtFullDate = findViewById(R.id.fullDateText);

            txtDayNumber.setText(dayStr);
            txtFullDate.setText(fullDateStr);
            popupWindow.dismiss();
        });
    }

    private void showIconPickerDialog(Context context, Consumer<String> onIconSelected) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.menu_icon);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    ContextCompat.getDrawable(context, R.drawable.bg_contained_18)  // hoặc bg bo tròn bạn đang dùng
            );
        }

        GridLayout iconGrid = dialog.findViewById(R.id.iconGrid);

        String[] iconNames = {
                "ic_date_and_time",
                "ic_image",
                "ic_video",
                "ic_star",
                "ic_checklist",
                "ic_chat"
        };

        for (String iconName : iconNames) {
            ImageView icon = new ImageView(context);
            int resId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());

            if (resId != 0) {
                icon.setImageResource(resId);
            } else {
                icon.setImageResource(R.drawable.ic_checklist);  // fallback
            }

            //  Đặt màu đồng nhất cho icon
            icon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

            icon.setPadding(16, 16, 16, 16);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 150;
            params.height = 150;
            icon.setLayoutParams(params);

            icon.setOnClickListener(v -> {
                onIconSelected.accept(iconName);
                dialog.dismiss();
            });

            iconGrid.addView(icon);
        }


        dialog.show();
    }

    private void showColorPickerDialog(Context context, Consumer<Integer> onColorSelected) {
        View menuView = LayoutInflater.from(context).inflate(R.layout.menu_color_task, null);

        PopupWindow popupWindow = new PopupWindow(menuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setElevation(10);
        popupWindow.showAtLocation(menuView.getRootView(), Gravity.CENTER, 0, 0);

        // Gán view từ layout
        ColorPickerView colorPickerView = menuView.findViewById(R.id.colorPickerView);
        View flagColorLayout = menuView.findViewById(R.id.flag_color_layout);
        TextView flagColorCode = menuView.findViewById(R.id.flag_color_code);
        ImageButton btnConfirm = menuView.findViewById(R.id.btnConfirmColor);

        final int[] selectedColor = {Color.parseColor("#26495C")}; // mặc định

        colorPickerView.setColorListener((ColorEnvelopeListener) (envelope, fromUser) -> {
            int color = envelope.getColor();
            String hex = envelope.getHexCode();
            flagColorLayout.setBackgroundColor(color);
            flagColorCode.setText("#" + hex);
            selectedColor[0] = color;
        });

        btnConfirm.setOnClickListener(v -> {
            onColorSelected.accept(selectedColor[0]); // callback truyền màu đã chọn
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
        popupWindow.showAsDropDown(anchor, 100, -60);

        ImageView taskIcon1 = menuView.findViewById(R.id.taskIcon1);
        ImageView editIcon = menuView.findViewById(R.id.editIcon);
        ImageView taskColor = menuView.findViewById(R.id.taskColor);
        ImageView editColor = menuView.findViewById(R.id.editColor);
        ImageButton confirmBtn = menuView.findViewById(R.id.addIconandColor);

        // Lấy view hiện tại trong activity_edit_task
        color_task = findViewById(R.id.color_task);  // CardView
        icTask = findViewById(R.id.icon_task);          // ImageView

        //  Hiển thị icon hiện tại
        taskIcon1.setImageDrawable(icTask.getDrawable());

        //  Hiển thị màu hiện tại (từ CardView)
        int currentColor = color_task.getCardBackgroundColor().getDefaultColor();
        taskColor.setBackgroundColor(currentColor);

        // Nhấn vào edit icon
        editIcon.setOnClickListener(v -> {
            showIconPickerDialog(this, iconName -> {
                selectedTaskIconName = iconName;
                int resId = getResources().getIdentifier(iconName, "drawable", getPackageName());
                taskIcon1.setImageResource(resId);
            });
        });

        // Nhấn vào edit màu
        editColor.setOnClickListener(v -> {
            showColorPickerDialog(this, color -> {
                selectedTaskColor = color;
                taskColor.setBackgroundColor(color);
            });
        });

        // Nhấn nút xác nhận
        confirmBtn.setOnClickListener(v -> {
            int resId = getResources().getIdentifier(selectedTaskIconName, "drawable", getPackageName());
            icTask.setImageResource(resId);
            color_task.setCardBackgroundColor(selectedTaskColor);
            popupWindow.dismiss();
        });
    }


}
