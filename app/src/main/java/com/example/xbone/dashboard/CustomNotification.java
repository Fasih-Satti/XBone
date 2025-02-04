package com.example.xbone.dashboard;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.example.xbone.R;

public class CustomNotification {

    private static final String CHANNEL_ID = "signup_channel";
    private static final int NOTIFICATION_ID = 1;

    public static void showSignupNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Request POST_NOTIFICATIONS permission for Android 13 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Create Notification Channel if needed (Android O and above)
        if (notificationManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Signup Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for signup notifications");

            // Ensure notification sound file is present in res/raw/notification_sound.wav
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification_sound);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(soundUri, audioAttributes);
            notificationManager.createNotificationChannel(channel);
        }

        // Prepare the notification sound
        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification_sound);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)  // Use your own notification icon
                .setContentTitle("Congratulations!")
                .setContentText("You have successfully registered for Xbone.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(soundUri)
                .build();

        // Notify the user
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
