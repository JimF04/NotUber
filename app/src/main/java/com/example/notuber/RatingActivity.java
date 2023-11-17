package com.example.notuber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends Activity {

    private RatingBar ratingBar;
    private Button submitRatingButton;
    private Button continueButton;
    private int ratingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingBar = findViewById(R.id.ratingBar);
        submitRatingButton = findViewById(R.id.submitRatingButton);
        continueButton = findViewById(R.id.continueButton);

        submitRatingButton.setOnClickListener(v -> {
            ratingValue = Math.round(ratingBar.getRating());
            Toast.makeText(getApplicationContext(), "Rating: " + ratingValue, Toast.LENGTH_SHORT).show();
            // Escribir funcion de actualizar rating en users.xml, etc.
        });

        continueButton.setOnClickListener(v -> {
            // regresa a menu principal de empleado
            Intent intent = new Intent(RatingActivity.this, EmployeeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}