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
import com.google.firebase.auth.FirebaseUser;

public class ThirdActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, usernameEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initUI();
        setupListeners();
    }

    private void initUI() {
        nameEditText = findViewById(R.id.etName);
        emailEditText = findViewById(R.id.etEmail);
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        Button signUpButton = findViewById(R.id.btnSignUp);
        TextView loginText = findViewById(R.id.tvLogin);
    }

    private void setupListeners() {
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    performSignUp();
                }
            }
        });

        findViewById(R.id.tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate name
        if (TextUtils.isEmpty(nameEditText.getText())) {
            nameEditText.setError("Name is required");
            isValid = false;
        }

        // Validate email
        if (TextUtils.isEmpty(emailEditText.getText())) {
            emailEditText.setError("Email is required");
            isValid = false;
        }

        // Validate username
        if (TextUtils.isEmpty(usernameEditText.getText())) {
            usernameEditText.setError("Username is required");
            isValid = false;
        }

        // Validate password
        if (TextUtils.isEmpty(passwordEditText.getText())) {
            passwordEditText.setError("Password is required");
            isValid = false;
        }

        return isValid;
    }

    private void performSignUp() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Firebase Authentication to create user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, navigate to login page (FourthActivity)
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(ThirdActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                        // Proceed to the login activity (FourthActivity)
                        Intent signUpIntent = new Intent(ThirdActivity.this, FourthActivity.class);
                        startActivity(signUpIntent);
                        finish(); // Optional: Close the ThirdActivity so the user cannot go back to it
                    } else {
                        // If sign up fails, display a message to the user
                        Toast.makeText(ThirdActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToLogin() {
        // Navigate to login activity (FourthActivity)
        Intent loginIntent = new Intent(ThirdActivity.this, FourthActivity.class);
        startActivity(loginIntent);
    }
}
