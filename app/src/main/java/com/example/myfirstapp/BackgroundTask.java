package com.example.myfirstapp;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
    private static String data = "";
    //using LinkedList<String> for effective concat
    private static List<String> dataParsed = new LinkedList<>();
    private static String singleParsed = "";
    //using HashSet, because only lookup and adding is needed on titleSet
    private static HashSet<String> titlesSet = new HashSet<>();
    // private static Integer calledTimes = 0;
    public static boolean isUpdated = false;

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

//            if (calledTimes.equals(0)) { //first time no check
//                for (int i = 0; i < articlesArray.length(); i++) {
//                    JSONObject currentArticle = articlesArray.getJSONObject(i);
//                    String title = currentArticle.getString("webTitle");
//                    //String pillarName = currentArticle.getString("pillarName");
//
//                    //todo IMAGE
//
//                    this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
//                            "Category:" + currentArticle.get("pillarName") + "\n";
//
//                    this.dataParsed.add(0, singleParsed);
//                    this.dataParsed.add(0, "\n");
//                }
//                calledTimes++;
//            } else {
            //The same article can be sent from API
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
                    isUpdated = true;
                }
            }
//            }
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

        writeToFile(viewData);

        MainActivity.data.setText(viewData);
    }

    private void writeToFile(String content) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/test.txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }
}

