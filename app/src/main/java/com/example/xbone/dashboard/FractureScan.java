package com.example.xbone.dashboard;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xbone.R;
import com.example.xbone.permissions.StoragePermission;

public class FractureScan extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView centerImage;
    private TextView fractureText, fractureStatus;
    private Button uploadButton, detectButton;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fracture_scan, container, false);

        centerImage = view.findViewById(R.id.center_image);
        fractureText = view.findViewById(R.id.fracture_txt);
        fractureStatus = view.findViewById(R.id.fracture_status);
        uploadButton = view.findViewById(R.id.upload_button);
        detectButton = view.findViewById(R.id.detect_button);

        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable("imageUri");
            if (imageUri != null) {
                loadImageFromUri(imageUri);
            }
        }

        uploadButton.setOnClickListener(v -> {
            if (StoragePermission.isStoragePermissionGranted(getActivity())) {
                openGallery();
            } else {
                StoragePermission.requestStoragePermission(getActivity());
            }
        });

        detectButton.setOnClickListener(v -> fractureStatus.setVisibility(View.VISIBLE));

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            imageUri = data.getData();
            loadImageFromUri(imageUri);
        }
    }

    private void loadImageFromUri(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            centerImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (imageUri != null) {
            outState.putParcelable("imageUri", imageUri);
        }
    }

    // Handling the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getActivity(), "Permission denied, cannot access gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
