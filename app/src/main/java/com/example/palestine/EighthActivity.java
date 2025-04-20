package com.example.palestine;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class EighthActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth);  // Ensure this is the correct layout file

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch the source link from Firestore
        fetchLinkFromFirestore();
    }

    // Method to fetch the link for Jerusalem from Firestore
    private void fetchLinkFromFirestore() {
        // Get a reference to the Firestore collection and document
        db.collection("jerusalem").document("RNasm4JpnC6vDy0tj8Da")
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

                                // Optionally, load the image related to Jerusalem
                                ImageView domeImageView = findViewById(R.id.domeImageView);
                                Glide.with(EighthActivity.this)
                                        .load(R.drawable.jerusalem_image)  // Load the new image
                                        .into(domeImageView);
                            } else {
                                // Link is empty or null
                                Toast.makeText(EighthActivity.this, "Link not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Document does not exist
                            Toast.makeText(EighthActivity.this, "Document not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Task failed
                        Toast.makeText(EighthActivity.this, "Error fetching link", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
