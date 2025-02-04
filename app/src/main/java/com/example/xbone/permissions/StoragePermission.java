package com.example.xbone.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class StoragePermission {

    public static void requestStoragePermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Toast.makeText(activity, "Storage permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isStoragePermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}
