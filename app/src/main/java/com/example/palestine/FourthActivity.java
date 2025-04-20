package com.example.palestine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FourthActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private FirebaseAuth mAuth;
    private TextView tvForgotPassword;
    private Button btnLogIn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, move to the next activity
            reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        //Connects the Java code with the XML UI elements.

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPassword = findViewById(R.id.forgotPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        //Password Visibility Toggle:
        // Handle password visibility toggle when clicking on the eye icon in the EditText
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int drawableRight = 2; // Index for drawableRight
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[drawableRight].getBounds().width())) {
                        // Toggle password visibility
                        if (etPassword.getTransformationMethod() != null) {
                            // Show password
                            etPassword.setTransformationMethod(null);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0); // Replace with your "eye on" icon
                        } else {
                            // Hide password
                            etPassword.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0); // Replace with your "eye off" icon
                        }
                    }
                }
                return false;
            }
        });

        // Handle "Forgot Password?" text click
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ResetActivity (Reset Password page)
                Intent intent = new Intent(FourthActivity.this, ResetActivity.class);
                startActivity(intent);
            }
        });

        // Handle Log In button click
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username (email) and password
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    // Perform Firebase login
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(FourthActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign-in success, navigate to the next activity
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(FourthActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                                    // Navigate to the next activity (e.g., FifthActivity)
                                    Intent intent = new Intent(FourthActivity.this, FifthActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign-in fails, display a message to the user
                                    Toast.makeText(FourthActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(FourthActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reload() {
        // You can add your reload logic here to handle user info once logged in
    }
}
