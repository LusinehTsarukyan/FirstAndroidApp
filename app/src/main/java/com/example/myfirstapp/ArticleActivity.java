package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

        imageData = (ImageView) findViewById(R.id.img_activity_2);
        summary = findViewById(R.id.summary);
        setTitle(String.valueOf(title));
    }
}
