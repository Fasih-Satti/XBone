package com.example.xbone.permissions;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NotificationPermission {

    public static void requestNotificationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
            } else {
                Toast.makeText(activity, "Notification permission already granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isNotificationPermissionGranted(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED;
    }
}

