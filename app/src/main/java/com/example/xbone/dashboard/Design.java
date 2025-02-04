package com.example.xbone.dashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.xbone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Design extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onBottomNavigationItemSelected);

        hideBottomNavLabels();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Home()).commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
            showBottomNavLabels(bottomNavigationView.getMenu().findItem(R.id.home));
        }

        findViewById(R.id.menu_icon).setOnClickListener(v -> openDrawerWithAnimation());
    }

    private boolean onBottomNavigationItemSelected(MenuItem item) {
        Fragment selectedFragment = null;
        hideBottomNavLabels();
        if (item.getItemId() == R.id.home) {
            selectedFragment = new Home();
        } else if (item.getItemId() == R.id.Diet) {
            selectedFragment = new Diet();
        } else if (item.getItemId() == R.id.FractureScan) {
            selectedFragment = new FractureScan();
        } else if (item.getItemId() == R.id.Chat) {
            selectedFragment = new Chatbot();
        } else if (item.getItemId() == R.id.Profile) {
            selectedFragment = new Profile();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, selectedFragment)
                    .commit();
        }

        showBottomNavLabels(item);
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;

        if (id == R.id.Diet) {
            selectedFragment = new Diet();
        } else if (id == R.id.Chat) {
            selectedFragment = new Chatbot();
            bottomNavigationView.setVisibility(View.GONE);
        } else if (id == R.id.FractureScan) {
            selectedFragment = new FractureScan();
        } else if (id == R.id.Profile) {
            selectedFragment = new Profile();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, selectedFragment)
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openDrawerWithAnimation() {
        drawerLayout.openDrawer(GravityCompat.START);  // Open the drawer
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void hideBottomNavLabels() {
        bottomNavigationView.getMenu().findItem(R.id.home).setTitle("");
        bottomNavigationView.getMenu().findItem(R.id.Diet).setTitle("");
        bottomNavigationView.getMenu().findItem(R.id.FractureScan).setTitle("");
        bottomNavigationView.getMenu().findItem(R.id.Chat).setTitle("");
        bottomNavigationView.getMenu().findItem(R.id.Profile).setTitle("");
    }

    private void showBottomNavLabels(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            bottomNavigationView.getMenu().findItem(R.id.home).setTitle("Home");
        } else if (item.getItemId() == R.id.Diet) {
            bottomNavigationView.getMenu().findItem(R.id.Diet).setTitle("Diet");
        } else if (item.getItemId() == R.id.FractureScan) {
            bottomNavigationView.getMenu().findItem(R.id.FractureScan).setTitle("X-Ray");
        } else if (item.getItemId() == R.id.Chat) {
            bottomNavigationView.getMenu().findItem(R.id.Chat).setTitle("Adviser");
        } else if (item.getItemId() == R.id.Profile) {
            bottomNavigationView.getMenu().findItem(R.id.Profile).setTitle("Profile");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
