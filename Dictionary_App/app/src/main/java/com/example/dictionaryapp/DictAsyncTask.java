package com.example.dictionaryapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DictAsyncTask extends AsyncTask<URL, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private final MainActivity activity;

    public DictAsyncTask(Activity activity) {
        this.activity = (MainActivity) activity;
    }

    @Override
    protected String doInBackground(URL... url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url[0].openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String cur;
                    while ((cur = in.readLine()) != null) {
                        sb.append(cur);
                    }
                    in.close();
                    connection.disconnect();
                    return sb.toString();
                case 404:
                    return "404";

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return "FAILED";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            activity.updateUI(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
