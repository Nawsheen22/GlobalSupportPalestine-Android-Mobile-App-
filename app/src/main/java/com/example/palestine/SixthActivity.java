package com.example.palestine;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import android.view.View;
public class SixthActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);  // Ensure this is the correct layout file

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch the source link from Firestore
        fetchLinkFromFirestore();
    }

    // Method to fetch the link for the Dome of the Rock from Firestore
    private void fetchLinkFromFirestore() {
        // Get a reference to the Firestore collection and document
        db.collection("dome_of_the_rock").document("I3UMgtzMx8xBpxrtfuqG")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Fetch the link from Firestore
                            String link = document.getString("link");

                            if (link != null && !link.isEmpty()) {
                                // Display the link in the TextView and make it clickable
                                TextView linkTextView = findViewById(R.id.sourceTextView);
                                linkTextView.setText("Source: " + link);  // Set the link text
                                Linkify.addLinks(linkTextView, Linkify.WEB_URLS);  // Make the link clickable

                                // Optionally, load the image related to the Dome of the Rock
                                ImageView domeImageView = findViewById(R.id.domeImageView);
                                Glide.with(SixthActivity.this)
                                        .load(R.drawable.domeofrock)  // Load the image you want
                                        .into(domeImageView);
                            } else {
                                // Link is empty or null
                                Toast.makeText(SixthActivity.this, "Link not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Document does not exist
                            Toast.makeText(SixthActivity.this, "Document not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Task failed
                        Toast.makeText(SixthActivity.this, "Error fetching link", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method for the "Next" button click
    public void goToNextActivity(View view) {
        // Start the next activity, e.g., SeventhActivity
        Intent intent = new Intent(SixthActivity.this, SeventhActivity.class);  // Change to your target activity
        startActivity(intent);
    }
}
