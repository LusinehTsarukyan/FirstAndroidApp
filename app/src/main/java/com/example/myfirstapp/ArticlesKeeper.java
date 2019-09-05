package com.example.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ArticlesKeeper{
    private HashSet<String> titlesSet = new HashSet<>();
    private List<Article> articleList = new LinkedList<>();
    public static NotificationManager notificationManager = null;
    public static Integer isUpdated = 0;

    ArticlesKeeper(Context context) {
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

            String viewData = " ";
            for (int i = 0; i < articlesArray.length(); i++) {
                Article currentArticle = new Article(articlesArray.getJSONObject(i));
                //todo IMAGE

                String singleParsed = "";
                //adding only new articles
                if (titlesSet.add(currentArticle.getTitle())) {
                    //adding all unique articles to the list
                    this.articleList.add(0, currentArticle);
                    viewData = viewData + "\n" + "Title:" + currentArticle.getTitle() + "\n" +
                            "Category:" + currentArticle.getPillarName() + "\n";

                    //rendering part
                    MainActivity.titleData.setText(currentArticle.getTitle());
                    MainActivity.pillarData.setText("Category: " + currentArticle.getPillarName());
                    this.isUpdated++;
                }
            }
            if (isUpdated > 0) {
                notificationManager.displayNotification(isUpdated);
                isUpdated = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
