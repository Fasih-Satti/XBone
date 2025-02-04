package com.example.xbone.authentication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.xbone.R;

public class ForgotPassword extends AppCompatActivity {

    //Declaration
    Button btnReset, btnBack;
    EditText edtEmail;
    ProgressBar progressBar;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initializaton
        btnBack = findViewById(R.id.btnForgotPasswordBack);
        btnReset = findViewById(R.id.btnReset);
        edtEmail = findViewById(R.id.edtForgotPasswordEmail);
        progressBar = findViewById(R.id.forgetPasswordProgressbar);


        //Reset Button Listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    edtEmail.setError("Email field can't be empty");
                }
            }
        });


        //Back Button Code
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void ResetPassword() {
        progressBar.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);
    }
}