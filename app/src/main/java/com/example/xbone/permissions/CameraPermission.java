package com.example.xbone.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraPermission {

    public static void requestCameraPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA}, 2);
        } else {
            Toast.makeText(activity, "Camera permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isCameraPermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}
