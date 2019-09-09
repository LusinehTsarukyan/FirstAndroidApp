package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class ArticlesManager {
    private HashSet<String> titlesSet = new HashSet<>(); //todo ID
    public  NotificationManager notificationManager;
    public static Integer isUpdated = 0;
    public RecyclerViewAdapter adapter;
    public HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;

    ArticlesManager(Activity activity) {
        this.notificationManager = new NotificationManager();
        this.adapter = new RecyclerViewAdapter(activity);
        this.horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(activity);
    }

    public void update(String jsonData) {
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonData);
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                Article currentArticle = new Article(articlesArray.getJSONObject(i));
                //adding only new articles
                if (titlesSet.add(currentArticle.getTitle())) {
                    adapter.addItemsToList(currentArticle);
                    horizontalRecyclerViewAdapter.addItemsToList(currentArticle);
                    this.isUpdated++;
                }
            }
            if (isUpdated > 0) {
                notificationManager.displayNotification(isUpdated);
                adapter.notifyDataSetChanged();
                horizontalRecyclerViewAdapter.notifyDataSetChanged();
                isUpdated = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
