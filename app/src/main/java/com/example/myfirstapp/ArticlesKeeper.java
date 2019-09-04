package com.example.myfirstapp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ArticlesKeeper {
    private HashSet<String> titlesSet = new HashSet<>();
    private List<String> dataParsed = new LinkedList<>();
    //public static NotificationManager notificationManager = new NotificationManager();
    private static Integer isUpdated = 0;


    public void update(String jsonData) {
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonData);

            //Create the JSONObject with the key "response"
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
            //JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of news stories.
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentArticle = articlesArray.getJSONObject(i);
                String title = currentArticle.getString("webTitle");
                //String img = currentArticle.getString("image");
                //String pillarName = currentArticle.getString("pillarName");

                //todo IMAGE

                String singleParsed = "";
                //adding only new articles
                if (titlesSet.add(title)) {
                    singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                            "Category:" + currentArticle.get("pillarName") + "\n";

                    this.dataParsed.add(0, singleParsed);
                    this.dataParsed.add(0, "\n");
                    isUpdated++;
                }
            }

            String viewData = " ";
            for (String str : dataParsed) {
                viewData = viewData + "\n" + str + "\n";
            }
            System.out.println("THIS PARSED " + viewData);

            MainActivity.data.setText(viewData);

            //todo
//            if (this.isUpdated > 0) {
//                notificationManager.displayNotification(this.isUpdated);
//                this.isUpdated = 0;
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
