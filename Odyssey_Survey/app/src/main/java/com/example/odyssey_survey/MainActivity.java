package com.example.odyssey_survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private RadioButton radioButtonType;
    private String name, role;

    String state = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.name_user);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button next = findViewById(R.id.next);

        userName.setImeOptions(EditorInfo.IME_ACTION_DONE);


        Toast.makeText(MainActivity.this, "State of MainActivity is in onCreate", Toast.LENGTH_SHORT).show();
        Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onCreate");
        state = "onCreate";


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonType = findViewById(checkedId);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().length() == 0)
                    Toast.makeText(MainActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                else if (!(userName.getText().toString().matches("[a-zA-Z]+( +[a-zA-Z]+)*")))
                    Toast.makeText(MainActivity.this, "Please enter a valid username having only letters", Toast.LENGTH_SHORT).show();
                else if (radioButtonType == null)
                    Toast.makeText(MainActivity.this, "Please select role type", Toast.LENGTH_SHORT).show();
                else {
                    String user_name = String.valueOf(userName.getText());
                    String role_type = String.valueOf(radioButtonType.getText());

                    Intent i = new Intent(MainActivity.this, activity2.class);

                    i.putExtra("user_name", user_name);
                    i.putExtra("role_type", role_type);

                    startActivity(i);
                }
            }
        });


        if (savedInstanceState != null) {
            name = savedInstanceState.getString("name");
            role = savedInstanceState.getString("role");
            userName.setText(name);
            if (radioButtonType != null)
                radioButtonType.setText(role);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name);
        outState.putString("role", role);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!state.isEmpty()) {
            Toast.makeText(MainActivity.this, "State of MainActivity changed from " + state + " to onStart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onStart");
        } else {
            Toast.makeText(MainActivity.this, "State of MainActivity is in onStart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity is in onStart");
        }
        state = "onStart";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!state.isEmpty()) {
            Toast.makeText(MainActivity.this, "State of MainActivity changed from " + state + " to onRestart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onRestart");
        } else {
            Toast.makeText(MainActivity.this, "State of MainActivity is in onRestart", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity is in onRestart");
        }
        state = "onRestart";
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!state.isEmpty()) {
            Toast.makeText(MainActivity.this, "State of MainActivity changed from " + state + " to onResume", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onResume");
        } else {
            Toast.makeText(MainActivity.this, "State of MainActivity is in onResume", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity is in onResume");
        }
        state = "onResume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!state.isEmpty()) {
            Toast.makeText(MainActivity.this, "State of MainActivity changed from " + state + " to onPause", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onPause");
        } else {

            Toast.makeText(MainActivity.this, "State of MainActivity is in onPause", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity is in onPause");
        }
        state = "onPause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!state.isEmpty()) {
            Toast.makeText(MainActivity.this, "State of MainActivity changed from " + state + " to onStop", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onStop");
        } else {
            Toast.makeText(MainActivity.this, "State of MainActivity is in onStop", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity is in onStop");
        }
        state = "onStop";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!state.isEmpty()) {
            Toast.makeText(MainActivity.this, "State of MainActivity changed from " + state + " to onDestroy", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity changed from " + state + " to onDestroy");
        } else {
            Toast.makeText(MainActivity.this, "State of MainActivity is in onDestroy", Toast.LENGTH_SHORT).show();
            Log.i(String.valueOf(MainActivity.class), "State of MainActivity is in onDestroy");
        }
        state = "onDestroy";
    }
}