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
import java.util.List;

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    private static String data = "";
    private static String dataParsed = "";
    private static String singleParsed = "";
    //using HashSet, because only lookup and adding is needed on titleSet
    private static HashSet<String> titlesSet = new HashSet<>();

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
//            }
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
        // Create an empty ArrayList that we can start adding
        List articlesList = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            //Create the JSONObject with the key "response"
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
            //JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of news stories.
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");
            //fast for only adding in the end of the list

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentArticle = articlesArray.getJSONObject(i);
                String title = currentArticle.getString("webTitle");
                //String pillarName = currentArticle.getString("pillarName");

                //todo IMAGE

                //adding only new articles
                if(titlesSet.add(title)) {
                    this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                            "Category:" + currentArticle.get("pillarName") + "\n";

                    this.dataParsed = this.singleParsed + "\n" + this.dataParsed + "\n";
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
        System.out.println("THIS PARSED " + this.dataParsed);
            MainActivity.data.setText(this.dataParsed);
    }
}

