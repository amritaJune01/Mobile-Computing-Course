package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button search_button = findViewById(R.id.search_button);
        word = findViewById(R.id.typeWord);

        search_button.setOnClickListener(view -> {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (word.getText().length() == 0) {
                Toast.makeText(MainActivity.this, "Please enter a word", Toast.LENGTH_SHORT).show();
            } else if (!(word.getText().toString().matches("[a-zA-Z]+( +[a-zA-Z]+)*"))) {
                Toast.makeText(MainActivity.this, "Please enter a word having only letters", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    getWordMeaning();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void getWordMeaning() throws IOException {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net_info = cm.getActiveNetworkInfo();

        if (net_info == null || !net_info.isConnected()) {
            Toast.makeText(this, "Not connected to any network", Toast.LENGTH_SHORT).show();
        } else {
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word.getText().toString().toLowerCase());
            AsyncTask<URL, Void, String> task = new DictAsyncTask(this).execute(url);
        }
    }

    void updateUI(String data) throws JSONException {
        Intent i = new Intent(this, WordInfoActivity.class);
        if (data == null || data.length() == 0 || data.compareTo("FAILED") == 0) {
            Toast.makeText(this, "Failed to receive any response!", Toast.LENGTH_LONG).show();
        } else if (data.compareTo("404") == 0) {
            Toast.makeText(this, "No Definitions found for the above word", Toast.LENGTH_LONG).show();
        } else {
            i.putExtra("word", String.valueOf(word.getText()));
            i.putExtra("JSONString", data);
            startActivity(i);
        }
    }
}
