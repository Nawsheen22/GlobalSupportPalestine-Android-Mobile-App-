package com.example.palestine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonationFormActivity extends AppCompatActivity {

    // Declare the UI elements
    EditText etName, etEmail, etAmount;
    Button btnDonate;

    // Firebase Database reference
    DatabaseReference databaseDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_form);  // Ensure the correct layout file

        // Initialize Firebase Database reference
        databaseDonations = FirebaseDatabase.getInstance().getReference("donations");

        // Initialize the UI elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAmount = findViewById(R.id.etPassword);  // Assuming this is the Amount field
        btnDonate = findViewById(R.id.btnSignUp);

        // Set the "Donate" button listener
        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDonation();
            }
        });
    }

    private void submitDonation() {
        // Get the text from the input fields
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();

        // Validate the inputs
        if (name.isEmpty() || email.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(DonationFormActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the amount is a valid number
        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(DonationFormActivity.this, "Amount must be a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique ID for each donation
        String donationId = databaseDonations.push().getKey();

        // Create a Donation object to store data in Firebase
        Donation donation = new Donation(name, email, amount);

        // Store the donation object in the Firebase database
        if (donationId != null) {
            databaseDonations.child(donationId).setValue(donation)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Donation successful, navigate to SuccessfulActivity
                            Intent intent = new Intent(DonationFormActivity.this, Successful.class);
                            startActivity(intent);
                            finish();  // Close the current activity so the user can't go back to it
                        } else {
                            Toast.makeText(DonationFormActivity.this, "Donation failed. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Donation class for structuring data
    public static class Donation {
        public String name, email;
        public double amount;

        public Donation() {
            // Default constructor required for calls to DataSnapshot.getValue(Donation.class)
        }

        public Donation(String name, String email, double amount) {
            this.name = name;
            this.email = email;
            this.amount = amount;
        }
    }
}
