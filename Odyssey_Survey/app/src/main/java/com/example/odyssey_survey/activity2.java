package com.example.odyssey_survey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class activity2 extends AppCompatActivity {

    private CheckBox musicCheckbox, danceCheckbox, playCheckbox, fashionCheckbox, foodCheckbox;

    private RatingBar musicRating, danceRating, playRating, fashionRating, foodRating;

    private String name, role;

    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        Intent intent = getIntent();
        name = intent.getStringExtra("user_name");
        role = intent.getStringExtra("role_type");

        musicCheckbox = findViewById(R.id.music);
        danceCheckbox = findViewById(R.id.dance);
        playCheckbox = findViewById(R.id.play);
        fashionCheckbox = findViewById(R.id.fashion);
        foodCheckbox = findViewById(R.id.food);

        musicRating = findViewById(R.id.music_ratings);
        danceRating = findViewById(R.id.dance_ratings);
        playRating = findViewById(R.id.play_ratings);
        fashionRating = findViewById(R.id.fashion_ratings);
        foodRating = findViewById(R.id.food_ratings);

        Button submit = findViewById(R.id.submit_button);

        Toast.makeText(activity2.this, "State of activity2 is in onCreate", Toast.LENGTH_SHORT).show();
        Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onCreate");
        state = "onCreate";

        if (savedInstanceState != null) {
            musicCheckbox.setChecked(savedInstanceState.getBoolean("music_checked"));
            danceCheckbox.setChecked(savedInstanceState.getBoolean("dance_checked"));
            playCheckbox.setChecked(savedInstanceState.getBoolean("play_checked"));
            fashionCheckbox.setChecked(savedInstanceState.getBoolean("fashion_checked"));
            foodCheckbox.setChecked(savedInstanceState.getBoolean("food_checked"));

            if (musicCheckbox.isChecked())
                musicRating.setVisibility(View.VISIBLE);
            else
                musicRating.setVisibility(View.GONE);

            if (danceCheckbox.isChecked())
                danceRating.setVisibility(View.VISIBLE);
            else
                danceRating.setVisibility(View.GONE);

            if (playCheckbox.isChecked())
                playRating.setVisibility(View.VISIBLE);
            else
                playRating.setVisibility(View.GONE);

            if (fashionCheckbox.isChecked())
                fashionRating.setVisibility(View.VISIBLE);
            else
                fashionRating.setVisibility(View.GONE);

            if (foodCheckbox.isChecked())
                foodRating.setVisibility(View.VISIBLE);
            else
                foodRating.setVisibility(View.GONE);

            musicRating.setRating(savedInstanceState.getFloat("music_rating"));
            danceRating.setRating(savedInstanceState.getFloat("dance_rating"));
            playRating.setRating(savedInstanceState.getFloat("play_rating"));
            fashionRating.setRating(savedInstanceState.getFloat("fashion_rating"));
            foodRating.setRating(savedInstanceState.getFloat("food_rating"));
        }


        submit.setOnClickListener(view -> {
            Toast.makeText(activity2.this, "Feedback has been recorded", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(activity2.this, activity3.class);

            i.putExtra("user_name", name);
            i.putExtra("role_type", role);

            i.putExtra("music_rating", musicRating.getRating());
            i.putExtra("dance_rating", danceRating.getRating());
            i.putExtra("play_rating", playRating.getRating());
            i.putExtra("fashion_rating", fashionRating.getRating());
            i.putExtra("food_rating", foodRating.getRating());

            i.putExtra("music_checked", musicCheckbox.isChecked());
            i.putExtra("dance_checked", danceCheckbox.isChecked());
            i.putExtra("play_checked", playCheckbox.isChecked());
            i.putExtra("fashion_checked", fashionCheckbox.isChecked());
            i.putExtra("food_checked", foodCheckbox.isChecked());

            startActivity(i);
        });

    }

    public void onMusicCheckboxClicked(View view) {
        musicCheckbox.setChecked(((CheckBox) view).isChecked());
        if (musicCheckbox.isChecked())
            musicRating.setVisibility(View.VISIBLE);
        else
            musicRating.setVisibility(View.GONE);
    }

    public void onDanceCheckboxClicked(View view) {
        danceCheckbox.setChecked(((CheckBox) view).isChecked());
        if (danceCheckbox.isChecked())
            danceRating.setVisibility(View.VISIBLE);
        else
            danceRating.setVisibility(View.GONE);
    }

    public void onPlayCheckboxClicked(View view) {
        playCheckbox.setChecked(((CheckBox) view).isChecked());
        if (playCheckbox.isChecked())
            playRating.setVisibility(View.VISIBLE);
        else
            playRating.setVisibility(View.GONE);
    }

    public void onFashionCheckboxClicked(View view) {
        fashionCheckbox.setChecked(((CheckBox) view).isChecked());
        if (fashionCheckbox.isChecked())
            fashionRating.setVisibility(View.VISIBLE);
        else
            fashionRating.setVisibility(View.GONE);
    }

    public void onFoodCheckboxClicked(View view) {
        foodCheckbox.setChecked(((CheckBox) view).isChecked());
        if (foodCheckbox.isChecked())
            foodRating.setVisibility(View.VISIBLE);
        else
            foodRating.setVisibility(View.GONE);
    }

    public void onClearPressed(View view) {
        musicCheckbox.setChecked(false);
        musicRating.setVisibility(View.GONE);
        musicRating.setRating(0);

        danceCheckbox.setChecked(false);
        danceRating.setVisibility(View.GONE);
        danceRating.setRating(0);

        playCheckbox.setChecked(false);
        playRating.setVisibility(View.GONE);
        playRating.setRating(0);

        fashionCheckbox.setChecked(false);
        fashionRating.setVisibility(View.GONE);
        fashionRating.setRating(0);

        foodCheckbox.setChecked(false);
        foodRating.setVisibility(View.GONE);
        foodRating.setRating(0);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("music_checked", musicCheckbox.isChecked());
        outState.putBoolean("dance_checked", danceCheckbox.isChecked());
        outState.putBoolean("play_checked", playCheckbox.isChecked());
        outState.putBoolean("fashion_checked", fashionCheckbox.isChecked());
        outState.putBoolean("food_checked", foodCheckbox.isChecked());

        outState.putFloat("music_rating", musicRating.getRating());
        outState.putFloat("dance_rating", danceRating.getRating());
        outState.putFloat("play_rating", playRating.getRating());
        outState.putFloat("fashion_rating", fashionRating.getRating());
        outState.putFloat("food_rating", foodRating.getRating());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!state.isEmpty()) {
            Toast.makeText(activity2.this, "State of activity2 changed from " + state + " to onStart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onStart");
        } else {
            Toast.makeText(activity2.this, "State of activity2 is in onStart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 is in onStart");
        }
        state = "onStart";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!state.isEmpty()) {
            Toast.makeText(activity2.this, "State of activity2 changed from " + state + " to onRestart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onRestart");
        } else {
            Toast.makeText(activity2.this, "State of activity2 is in onRestart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 is in onRestart");
        }
        state = "onRestart";
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!state.isEmpty()) {
            Toast.makeText(activity2.this, "State of activity2 changed from " + state + " to onResume", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onResume");
        } else {
            Toast.makeText(activity2.this, "State of activity2 is in onResume", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 is in onResume");
        }
        state = "onResume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!state.isEmpty()) {
            Toast.makeText(activity2.this, "State of activity2 changed from " + state + " to onPause", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onPause");
        } else {

            Toast.makeText(activity2.this, "State of activity2 is in onPause", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 is in onPause");
        }
        state = "onPause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!state.isEmpty()) {
            Toast.makeText(activity2.this, "State of activity2 changed from " + state + " to onStop", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onStop");
        } else {
            Toast.makeText(activity2.this, "State of activity2 is in onStop", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 is in onStop");
        }
        state = "onStop";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!state.isEmpty()) {
            Toast.makeText(activity2.this, "State of activity2 changed from " + state + " to onDestroy", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 changed from " + state + " to onDestroy");
        } else {
            Toast.makeText(activity2.this, "State of activity2 is in onDestroy", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(activity2.class), "State of activity2 is in onDestroy");
        }
        state = "onDestroy";
    }
}
