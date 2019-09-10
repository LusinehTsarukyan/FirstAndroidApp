package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

public class ArticleActivity extends AppCompatActivity {
    public static ImageView imageData;
    public static TextView title;
    public static TextView summary;
    ContexManager contexManager;
    Article currentArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        contexManager.setArticleContext(this);

        imageData = findViewById(R.id.img_activity_2);
        summary = findViewById(R.id.summary);
        title = findViewById(R.id.title);

        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("imagedata");
        imageData.setImageBitmap(bitmap);

        String mTitle = intent.getStringExtra("title");
        getSupportActionBar().setTitle(mTitle);
        title.setText(mTitle);

        String mSummary = intent.getStringExtra("summary");
        summary.setText(mSummary);

        String id = intent.getStringExtra("id");
        currentArticle = ArticlesManager.getArticleById(id);
    }

    public void addItemsToList(View view) {
        if(!ArticlesManager.horizontalRecyclerViewAdapter.pinnedArticles.contains(currentArticle)) {
            ArticlesManager.horizontalRecyclerViewAdapter.addItemsToList(currentArticle);
            ArticlesManager.horizontalRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
