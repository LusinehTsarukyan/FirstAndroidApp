package com.example.myfirstapp;

import android.app.Application;
import android.content.Context;

public class ContexManager extends Application {
    private static Context mainContext;
    private static Context articleContext;

    public static Context getMainContext() {return mainContext;}
    public static void setMainContext(Context mContext) {
        ContexManager.mainContext = mContext;
    }

    public static Context getArticleContext() {return articleContext;}
    public static void setArticleContext(Context articleContext) {ContexManager.articleContext = articleContext;}
}
