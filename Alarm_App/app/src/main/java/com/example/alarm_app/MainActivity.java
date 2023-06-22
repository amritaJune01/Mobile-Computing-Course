package com.example.alarm_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FirstFragment firstFragmentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#263238"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        if (savedInstanceState == null) {
            firstFragmentPage = new FirstFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, firstFragmentPage, "first_fragment");
            fragmentTransaction.commit();
        } else {
            firstFragmentPage = (FirstFragment) getSupportFragmentManager().findFragmentByTag("first_fragment");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "first_fragment", firstFragmentPage);
    }
}