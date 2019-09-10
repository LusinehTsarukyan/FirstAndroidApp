package com.example.myfirstapp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ArticlesManager {
    //using HashMap for fast lookup and part of solution of pinned articles view
    private static HashMap<String, Article> articleMap = new HashMap<>();
    public NotificationManager notificationManager;
    public Integer isUpdated = 0;
    public RecyclerViewAdapter adapter;
    public static HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;
//    private int test = 0;

    ArticlesManager(Activity activity) {
        this.notificationManager = new NotificationManager();
        this.adapter = new RecyclerViewAdapter(activity);
        this.horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(activity);
    }

    public static Article getArticleById(String id) {
        return articleMap.get(id);
    }

    public void update(String jsonData) {
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonData);
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                Article currentArticle = new Article(articlesArray.getJSONObject(i));
                //adding only new articles
                if (!articleMap.containsKey(currentArticle.getId())) {
                    articleMap.put(currentArticle.getId(), currentArticle);
                    adapter.addItemsToList(currentArticle);
                    this.isUpdated++;
                }

//                    Article testArticle = new Article();
//                    adapter.addItemsToList(testArticle);
//                    if (test == 1){
//                        isUpdated++;
//                    }
            }
            if (isUpdated > 0) {
                notificationManager.displayNotification(isUpdated);
                adapter.notifyDataSetChanged();
                isUpdated = 0;
//                test++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
