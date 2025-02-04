package com.example.xbone.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.xbone.R;
import com.example.xbone.dashboard.Design;

import android.util.Patterns;

public class LoginTab extends Fragment {

    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private TextView forgotPasswordLink;
    private CheckBox showPasswordCheckbox;

    public LoginTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        loginEmail = view.findViewById(R.id.login_email);
        loginPassword = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.login_button);
        forgotPasswordLink = view.findViewById(R.id.forgot_password_link);
        showPasswordCheckbox = view.findViewById(R.id.show_password_checkbox);

        // Toggle password visibility
        showPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                loginPassword.setInputType(0x90); // Show password
            } else {
                loginPassword.setInputType(0x81); // Hide password
            }
        });

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (email.isEmpty()) {
                loginEmail.setError("Email is required");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginEmail.setError("Please enter a valid email address");
            } else if (password.isEmpty()) {
                loginPassword.setError("Password is required");
            } else {
                Toast.makeText(getActivity(), "Login Successful ✅", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), Design.class);
                startActivity(intent);

            }
        });

        // Handle forgot password link click
        forgotPasswordLink.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ForgotPassword.class);
            startActivity(intent);
        });

        return view;
    }
}
