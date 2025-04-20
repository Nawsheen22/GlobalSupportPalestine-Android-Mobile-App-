package com.example.palestine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private TextView backButton;
    private FirebaseAuth mAuth;  // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset); // Make sure the layout name matches

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        initUI();

        // Setup listeners for buttons
        setupListeners();
    }

    private void initUI() {
        emailEditText = findViewById(R.id.etEmail);
        resetPasswordButton = findViewById(R.id.btnResetPassword);
        backButton = findViewById(R.id.tvBack);
    }

    private void setupListeners() {
        // Reset Password button click
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        // Back button click, navigate back to FourthActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetActivity.this, FourthActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void resetPassword() {

        //Gets the email from the input box:
        String email = emailEditText.getText().toString().trim();
//Checks if the email is empty:
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (!isValidEmail(email)) {
            emailEditText.setError("Invalid email");
            return;
        }

        // Send password reset email via Firebase Authentication
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // If the reset email is sent successfully
                        Toast.makeText(ResetActivity.this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();
                        // Optionally, navigate back to the login screen or show instructions
                        finish();
                    } else {
                        // If something goes wrong (e.g., email doesn't exist in the system)
                        Toast.makeText(ResetActivity.this, "Failed to send reset email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isValidEmail(String email) {
        // Check if the email is valid using Android's built-in email pattern
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
