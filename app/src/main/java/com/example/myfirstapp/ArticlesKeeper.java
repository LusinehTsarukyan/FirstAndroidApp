package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ArticlesKeeper {
    private HashSet<String> titlesSet = new HashSet<>();
    private List<String> dataParsed = new LinkedList<>();
    public static NotificationManager notificationManager = null;
    public static Integer isUpdated = 0;

    ArticlesKeeper(Context context){
        notificationManager = new NotificationManager(context);
    }

    public void update(String jsonData) {
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonData);

            //Create the JSONObject with the key "response"
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of news stories.
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                Article currentArticle = new Article(articlesArray.getJSONObject(i));
                //todo IMAGE

                String singleParsed = "";
                //adding only new articles
                if (titlesSet.add(currentArticle.getTitle())) {
                    singleParsed = "Title:" + currentArticle.getTitle() + "\n" +
                            "Category:" + currentArticle.getPillarName() + "\n";

                    this.dataParsed.add(0, singleParsed);
                    this.dataParsed.add(0, "\n");
                    this.isUpdated++;
                }
            }

            String viewData = " ";
            for (String str : dataParsed) {
                viewData = viewData + "\n" + str + "\n";
            }
            System.out.println("THIS PARSED " + viewData);

            MainActivity.data.setText(viewData);
            if(isUpdated > 0){
                notificationManager.displayNotification(isUpdated);
                isUpdated = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
