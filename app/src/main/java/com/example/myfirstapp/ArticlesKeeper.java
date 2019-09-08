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
    public List<Article> articleList = new LinkedList<>();
    public static NotificationManager notificationManager = null;
    public static Integer isUpdated = 0;
    public Context context;
    public MyRecyclerViewAdapter adapter;

    ArticlesKeeper(Activity activity) {
        notificationManager = new NotificationManager(context);
        this.context = context;
        adapter = new MyRecyclerViewAdapter(activity, articleList);
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
                    this.isUpdated++;
                }
            }
            if (isUpdated > 0) {
                notificationManager.displayNotification(isUpdated);
                adapter.notifyDataSetChanged();
                isUpdated = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
