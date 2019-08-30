package com.example.myfirstapp;

import android.os.AsyncTask;
import android.text.TextUtils;
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
import java.util.List;

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://content.guardianapis.com/search?q=article&api-key=6cb66347-dd59-4b6c-be55-731200528471");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            extractFeatureFromJson(data);
//            JSONArray JA = new JSONArray(data);
//            for (int i = 0; i < JA.length(); i++) {
//                JSONObject JO = (JSONObject) JA.get(i);
//                singleParsed = "Title:" + JO.get("webTitle") + "\n" +
//                        "Category:" + JO.get("pillarName") + "\n";
//
//                dataParsed = dataParsed + singleParsed + "\n";
//                System.out.println("JSON HERE " + JO);
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } /*catch (JSONException exception) {
            exception.printStackTrace();
        }*/ catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private void extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            //
        }

        // Create an empty ArrayList that we can start adding
        List articlesList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
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

                // Extract the value for the key called "sectionName"
                String pillarName = currentArticle.getString("pillarName");

                //Extract the JSONArray with the key "tag"
                //JSONArray tagsArray = currentArticle.getJSONArray("tags");

//                // Create a new NewsStory object with the title, section name, date,
//                // and url from the JSON response.
//                JSONObject article = new JSONObject(title, pillarName);
//
//                // Add the new NewsStory to the list of newsStories.
//                articlesList.add(article);
                this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                        "Category:" + currentArticle.get("pillarName") + "\n";

                this.dataParsed = this.dataParsed + this.singleParsed + "\n";
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println("THIS PARSED " + this.dataParsed);
        MainActivity.data.setText(this.dataParsed);
    }
}

