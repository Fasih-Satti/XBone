package com.example.xbone.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.xbone.R;
import com.example.xbone.dashboard.Design;
import com.example.xbone.dashboard.CustomNotification;

public class Signup extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        EditText username = view.findViewById(R.id.signup_username);
        EditText email = view.findViewById(R.id.signup_email);
        EditText password = view.findViewById(R.id.signup_password);
        CheckBox confirmCheckBox = view.findViewById(R.id.confirm_password_checkbox);
        Button signupButton = view.findViewById(R.id.signup_button);

        password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        signupButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (TextUtils.isEmpty(usernameText)) {
                Toast.makeText(getContext(), "Username is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(emailText)) {
                Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordText)) {
                Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (passwordText.length() < 5) {
                Toast.makeText(getContext(), "Password must be at least 5 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!confirmCheckBox.isChecked()) {
                Toast.makeText(getContext(), "Please confirm the information", Toast.LENGTH_SHORT).show();
                return;
            }

            CustomNotification.showSignupNotification(getContext());

            Intent intent = new Intent(getActivity(), Design.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}
