package com.example.xbone.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.xbone.R;
import com.example.xbone.permissions.NotificationPermission;

import java.util.ArrayList;
import java.util.List;

public class First extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ImageSliderAdapter adapter;
    private Handler handler;
    private Runnable runnable;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        viewPager = findViewById(R.id.image_slider);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.image_3);
        imageList.add(R.drawable.image_1);
        imageList.add(R.drawable.image_2);

        adapter = new ImageSliderAdapter(imageList);
        viewPager.setAdapter(adapter);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == imageList.size()) {
                    currentPosition = 0;
                }
                viewPager.setCurrentItem(currentPosition++, true);
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);

        Button letsStartButton = findViewById(R.id.lets_start_button);
        letsStartButton.setOnClickListener(v -> {
            // Check and request notification permission
            if (!NotificationPermission.isNotificationPermissionGranted(First.this)) {
                NotificationPermission.requestNotificationPermission(First.this);
            } else {
                // Proceed with the next activity if permission is granted
                Intent intent = new Intent(First.this, Background.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }
}
