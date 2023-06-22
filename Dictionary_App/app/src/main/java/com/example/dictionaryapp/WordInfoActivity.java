package com.example.dictionaryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WordInfoActivity extends AppCompatActivity {

    private TextView staticWord;

    private String word_audio = "", word_audio_path = "";
    boolean audio_found = false;

    private final List<Meaning> meaningList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);

        Intent intent = getIntent();

        staticWord = findViewById(R.id.static_word);
        ImageButton audioButton = findViewById(R.id.audio_button);

        staticWord.setText(intent.getStringExtra("word"));

        String data = intent.getStringExtra("JSONString");
        Gson gson = new Gson();
        try {
            String data1 = "{\"data\":" + data + "}";
            WordData word_data = gson.fromJson(data1, WordData.class);

            for (int i = 0; i < word_data.getList().size(); i++) {
                List<Meaning> temp = word_data.getList().get(i).getMeanings();
                meaningList.addAll(temp);
            }

            for (int i = 0; i < word_data.getList().size(); i++) {
                List<Phonetic> temp = word_data.getList().get(i).getPhonetics();
                for (int j = 0; j < temp.size(); j++) {
                    String audio = temp.get(j).getAudio();
                    if (audio != null && audio.length() != 0) {
                        word_audio = audio;
                        audio_found = true;
                        break;
                    }
                    if (audio_found) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getIntent().putExtra("meanings_list", (Serializable) meaningList);

        WordListFragment wordListFragment = new WordListFragment();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("detail_frag");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof WordDetailFragment) {
            fragmentTransaction.replace(R.id.posListFragment, fragment);

        } else {
            fragmentTransaction.replace(R.id.posListFragment, wordListFragment);
        }
        fragmentTransaction.commit();

        audioButton.setOnClickListener(v -> {
            if (word_audio_path.length() == 0) {
                try {
                    URL word_url;
                    word_url = new URL(word_audio);
                    new download_word_audio().execute(word_url);
                    Toast.makeText(getBaseContext(), "Download completed", Toast.LENGTH_SHORT).show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else play_word_audio();
        });

        if(savedInstanceState != null){
            word_audio_path = savedInstanceState.getString("word_audio_path");
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class download_word_audio extends AsyncTask<URL, Integer, String> {
        @Override
        protected String doInBackground(URL... urls) {
            int count;
            try {
                URLConnection connection = urls[0].openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(urls[0].openStream());
                OutputStream output = new FileOutputStream("/storage/emulated/0/Download/" + staticWord.getText() + ".mp3");

                word_audio_path = "/storage/emulated/0/Download/" + staticWord.getText() + ".mp3";

                byte[] data = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            play_word_audio();
        }
    }

    private void play_word_audio() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(word_audio_path);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("word_audio_path",word_audio_path);
    }
}