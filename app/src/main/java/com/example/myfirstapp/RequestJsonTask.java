package com.example.myfirstapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestJsonTask extends AsyncTask<String, Void, String> {
    private ArticlesManager articlesManager;

    public RequestJsonTask(ArticlesManager artKpr) {
        articlesManager = artKpr;
    }

    @Override
    protected String doInBackground(String... source) {
        return getJSONString(source[0]);
    }

    public static String getJSONString(String path) {
        String wholeJson = "";

        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                wholeJson = wholeJson + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wholeJson;
    }

    @Override
    protected void onPostExecute(String wholeJson) {
        this.articlesManager.update(wholeJson);
    }
}
