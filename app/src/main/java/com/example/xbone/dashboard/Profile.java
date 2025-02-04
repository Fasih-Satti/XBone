package com.example.xbone.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.xbone.R;
import com.example.xbone.authentication.LoginTab;

public class Profile extends Fragment {

    private ImageView profileImage;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    public Profile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        TextView userName = view.findViewById(R.id.user_name);
        TextView userEmail = view.findViewById(R.id.user_email_text);
        TextView userPassword = view.findViewById(R.id.password_text);
        Button logoutButton = view.findViewById(R.id.logout_button);

        // Register the activity result launcher
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                profileImage.setImageURI(selectedImageUri);
            }
        });

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);  // Use the launcher to start the activity
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginTab.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}
