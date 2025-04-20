package com.example.palestine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;  // Change to ImageButton to match the layout XML
import androidx.appcompat.app.AppCompatActivity;

public class DonateOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_option);  // Ensure this is the correct layout file

        // Initialize ImageButtons (not Button)
        ImageButton medicalButton = findViewById(R.id.btnMedical);
        ImageButton repairingButton = findViewById(R.id.btnRepairing);
        ImageButton foodButton = findViewById(R.id.btnFood);
        ImageButton waterButton = findViewById(R.id.btnWater);

        // Set click listeners for each ImageButton to navigate to the SixthActivity
        medicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSixthActivity("Medical");
            }
        });

        repairingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSixthActivity("Repairing");
            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSixthActivity("Food");
            }
        });

        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSixthActivity("Water");
            }
        });
    }

    // Helper function to navigate to SixthActivity with a donation category
    private void navigateToSixthActivity(String category) {
        Intent intent = new Intent(DonateOptionActivity.this, DonationFormActivity.class);
        intent.putExtra("donation_category", category); // Pass the category to the SixthActivity
        startActivity(intent);
    }
}
