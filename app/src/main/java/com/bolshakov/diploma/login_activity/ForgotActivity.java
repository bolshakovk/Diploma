package com.bolshakov.diploma.login_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bolshakov.diploma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    private Button resetPasswordBtn;
    private EditText email;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        resetPasswordBtn = (Button) findViewById(R.id.buttonReset);
        email = (EditText) findViewById(R.id.emailForgotText);
        progressBar = (ProgressBar) findViewById(R.id.progressForgotBar);
        auth = FirebaseAuth.getInstance();
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String checkEmail = email.getText().toString().trim();
        if (checkEmail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches()){
            email.setError("Not valid email!");
            email.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(checkEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ForgotActivity.this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}