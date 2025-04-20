package com.example.palestine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class FifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);  // Ensure this is your correct layout file

        // Initialize ImageView and load the GIF named 'blast' with Glide
        ImageView gifImageView = findViewById(R.id.gifImageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.blast) // Make sure this matches the name of your GIF file
                .into(gifImageView);

        // Setup the "Donate" button
        Button donateButton = findViewById(R.id.btnDonate);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DonateOptionActivity when "Donate" button is clicked
                Intent intent = new Intent(FifthActivity.this, DonateOptionActivity.class);
                startActivity(intent);
            }
        });

        // Setup the "More" button
        Button moreButton = findViewById(R.id.btnMore);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SixthActivity when "More" button is clicked
                Intent intent = new Intent(FifthActivity.this, SixthActivity.class);
                startActivity(intent);
            }
        });

        // Setup the "Log Out" button
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform log out action (e.g., clearing session or user data)

                // Example: clearing shared preferences (if you're using them)
                // SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                // SharedPreferences.Editor editor = preferences.edit();
                // editor.clear();  // Clear stored user data
                // editor.apply();

                // Redirect to LoginActivity (or whichever activity you want after logging out)
                Intent intent = new Intent(FifthActivity.this, FourthActivity.class);
                startActivity(intent);
                finish();  // Close the current activity so the user can't go back to it
            }
        });
    }
}
