package com.example.protrack.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.protrack.R;

public class Utils {
    public static String limitString(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength
                ? text.substring(0, maxLength) + "..."
                : text;
    }

    public static void showDialog(Context context, String title, String message, String okText, int iconResId, View.OnClickListener onOkClick) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_notification);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    ContextCompat.getDrawable(context, R.drawable.bg_contained_18)
            );
        }

        ((TextView) dialog.findViewById(R.id.title)).setText(title);
        ((TextView) dialog.findViewById(R.id.message)).setText(message);
        ((Button) dialog.findViewById(R.id.btn_ok)).setText(okText);
        ((ImageView) dialog.findViewById(R.id.icon)).setImageResource(iconResId);

        dialog.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            dialog.dismiss();
            if (onOkClick != null) onOkClick.onClick(v); // gọi callback nếu có
        });
        dialog.show();
    }
}
