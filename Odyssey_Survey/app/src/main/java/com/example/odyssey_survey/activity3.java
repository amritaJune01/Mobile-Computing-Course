package com.example.odyssey_survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class activity3 extends AppCompatActivity {

    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        String name = intent.getStringExtra("user_name");
        String role = intent.getStringExtra("role_type");

        boolean musicChecked = intent.getBooleanExtra("music_checked", false);
        boolean danceChecked = intent.getBooleanExtra("dance_checked", false);
        boolean playChecked = intent.getBooleanExtra("play_checked", false);
        boolean fashionChecked = intent.getBooleanExtra("fashion_checked", false);
        boolean foodChecked = intent.getBooleanExtra("food_checked", false);

        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText(name);
        TextView roleTextView = findViewById(R.id.role);
        roleTextView.setText(role);

        Toast.makeText(activity3.this, "State of activity3 is in onCreate", Toast.LENGTH_SHORT).show();
        Log.i(String.valueOf(activity3.class), "State of activity3 changed from " + state + " to onCreate");
        state = "onCreate";


        RatingBar musicRatingBar = findViewById(R.id.music_ratings_view);
        TextView musicText = findViewById(R.id.musicR_submitted);
        if (musicChecked) {
            musicText.setVisibility(View.VISIBLE);
            musicRatingBar.setVisibility(View.VISIBLE);
            musicRatingBar.setRating(intent.getFloatExtra("music_rating", 0));
        }

        RatingBar danceRatingBar = findViewById(R.id.dance_ratings_view);
        TextView danceText = findViewById(R.id.danceR_submitted);
        if (danceChecked) {
            danceText.setVisibility(View.VISIBLE);
            danceRatingBar.setVisibility(View.VISIBLE);
            danceRatingBar.setRating(intent.getFloatExtra("dance_rating", 0));
        }

        RatingBar playRatingBar = findViewById(R.id.play_ratings_view);
        TextView playText = findViewById(R.id.playR_submitted);
        if (playChecked) {
            playText.setVisibility(View.VISIBLE);
            playRatingBar.setVisibility(View.VISIBLE);
            playRatingBar.setRating(intent.getFloatExtra("play_rating", 0));
        }

        RatingBar fashionRatingBar = findViewById(R.id.fashion_ratings_view);
        TextView fashionText = findViewById(R.id.fashionR_submitted);
        if (fashionChecked) {
            fashionText.setVisibility(View.VISIBLE);
            fashionRatingBar.setVisibility(View.VISIBLE);
            fashionRatingBar.setRating(intent.getFloatExtra("fashion_rating", 0));
        }

        RatingBar foodRatingBar = findViewById(R.id.food_ratings_view);
        TextView foodText = findViewById(R.id.foodR_submitted);
        if (foodChecked) {
            foodText.setVisibility(View.VISIBLE);
            foodRatingBar.setVisibility(View.VISIBLE);
            foodRatingBar.setRating(intent.getFloatExtra("food_rating", 0));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!state.isEmpty()) {
            Toast.makeText(activity3.this, "State of activity3 changed from " + state + " to onStart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity3 changed from " + state + " to onStart");
        } else {
            Toast.makeText(activity3.this, "State of activity3 is in onStart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 is in onStart");
        }
        state = "onStart";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!state.isEmpty()) {
            Toast.makeText(activity3.this, "State of activity3 changed from " + state + " to onRestart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 changed from " + state + " to onRestart");
        } else {
            Toast.makeText(activity3.this, "State of activity3 is in onRestart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 is in onRestart");
        }
        state = "onRestart";
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!state.isEmpty()) {
            Toast.makeText(activity3.this, "State of activity3 changed from " + state + " to onResume", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 changed from " + state + " to onResume");
        } else {
            Toast.makeText(activity3.this, "State of activity3 is in onResume", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 is in onResume");
        }
        state = "onResume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!state.isEmpty()) {
            Toast.makeText(activity3.this, "State of activity3 changed from " + state + " to onPause", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 changed from " + state + " to onPause");
        } else {

            Toast.makeText(activity3.this, "State of activity3 is in onPause", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 is in onPause");
        }
        state = "onPause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!state.isEmpty()) {
            Toast.makeText(activity3.this, "State of activity3 changed from " + state + " to onStop", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 changed from " + state + " to onStop");
        } else {
            Toast.makeText(activity3.this, "State of activity3 is in onStop", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 is in onStop");
        }
        state = "onStop";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!state.isEmpty()) {
            Toast.makeText(activity3.this, "State of activity3 changed from " + state + " to onDestroy", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 changed from " + state + " to onDestroy");
        } else {
            Toast.makeText(activity3.this, "State of activity3 is in onDestroy", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity3.class), "State of activity3 is in onDestroy");
        }
        state = "onDestroy";
    }
}