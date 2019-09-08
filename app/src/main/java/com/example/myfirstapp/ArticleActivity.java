package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArticleActivity extends AppCompatActivity {
    public static ImageView imageData;
    public static TextView title;
    public static TextView summary;
    ContexManager contexManager;

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
    }
}
