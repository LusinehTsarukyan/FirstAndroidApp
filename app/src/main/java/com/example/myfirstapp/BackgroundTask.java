package com.example.myfirstapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    private static String JSONData = "";
    //using LinkedList<String> for effective concat
    private static List<String> dataParsed = new LinkedList<>();
    private static String singleParsed = "";
    //using HashSet, because only lookup and adding is needed on titleSet
    private static HashSet<String> titlesSet = new HashSet<>();
    public static int isUpdated = 0;
    private static String url = null;
    private static boolean isItFirstTime = true;

    public BackgroundTask(String url) {
        this.url = url;
        if (isItFirstTime) {
            try {
                URL url1 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    JSONData = JSONData + line;
                }
                // extractFeatureFromJson
                try {
                    JSONObject baseJsonResponse = new JSONObject(JSONData);
                    JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
                    JSONArray articlesArray = responseJSONObject.getJSONArray("results");
                    for (int i = 0; i < articlesArray.length(); i++) {
                        JSONObject currentArticle = articlesArray.getJSONObject(i);
                        String title = currentArticle.getString("webTitle");
                        this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                                "Category:" + currentArticle.get("pillarName") + "\n";
                        this.dataParsed.add(0, singleParsed);
                        this.dataParsed.add(0, "\n");
                        titlesSet.add(title); // No sense as it is checking if contains the title anyway
                        isItFirstTime = false;
                    }
                } catch (JSONException ex) {
                    Log.println(Log.ERROR, "Failure.", "JSONException");
                    ex.printStackTrace();
                }

            } catch (MalformedURLException e) {
                Log.println(Log.ERROR, "Failure.", "MalformedURLException");
                e.printStackTrace();
            } catch (IOException exception) {
                Log.println(Log.ERROR, "Failure.", "IOException");
                exception.printStackTrace();
            }
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                JSONData = JSONData + line;
            }
            extractFeatureFromJson(JSONData);
        } catch (MalformedURLException e) {
            Log.println(Log.ERROR, "Failure.", "MalformedURLException");
            e.printStackTrace();
        } catch (IOException exception) {
            Log.println(Log.ERROR, "Failure.", "IOException");
            exception.printStackTrace();
        }
        return null;
    }

    private void extractFeatureFromJson(String articleJSON) {
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            //Create the JSONObject with the key "response"
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
            //JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of news stories.
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentArticle = articlesArray.getJSONObject(i);
                String title = currentArticle.getString("webTitle");
                //String pillarName = currentArticle.getString("pillarName");

                //todo IMAGE

                //adding only new articles
                if (titlesSet.add(title)) {
                    this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                            "Category:" + currentArticle.get("pillarName") + "\n";

                    this.dataParsed.add(0, singleParsed);
                    this.dataParsed.add(0, "\n");
                    isUpdated++;
                }
            }
        } catch (JSONException ex) {
            Log.println(Log.ERROR, "Failure.", "JSONException");
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String viewData = " ";
        for (String str : dataParsed) {
            viewData = str + "\n" + viewData + "\n";
        }
        System.out.println("THIS PARSED " + viewData);

        MainActivity.data.setText(viewData);
    }
}

