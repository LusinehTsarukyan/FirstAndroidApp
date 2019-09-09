package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    public static ArticlesManager articlesManager = null;
    public static final String apiKey = "6cb66347-dd59-4b6c-be55-731200528471";
    private RecyclerViewAdapter adapter;
    private HorizontalRecyclerViewAdapter horizontalAdapter;
    ContexManager contexManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexManager.setMainContext(this);
        articlesManager = new ArticlesManager(this);

        final String urlString = "https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=" + apiKey;
        new RequestJsonTask(articlesManager).execute(urlString);

        // set up the RecyclerView
        RecyclerView horizontalRecyclerView = findViewById(R.id.pinnedArticles);
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(horizontalLinearLayoutManager);
        horizontalAdapter = articlesManager.horizontalRecyclerViewAdapter;

        horizontalAdapter.setClickListener(new HorizontalRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Article article) {
                ImageView imageView = view.findViewById(R.id.pinnedImg);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("summary", article.getSummary());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(), imageView
                        , "img_transition");
                startActivity(intent, option.toBundle());
            }
        });
        horizontalRecyclerView.setAdapter(horizontalAdapter);


        RecyclerView recyclerView = findViewById(R.id.articles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = articlesManager.adapter;

        adapter.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Article article) {
                ImageView imageView = view.findViewById(R.id.img_activity_1);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("summary", article.getSummary());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(), imageView
                        , "img_transition");
                startActivity(intent, option.toBundle());
            }
        });
        recyclerView.setAdapter(adapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          new RequestJsonTask(articlesManager).execute(urlString);

                                      }
                                  },
                0, 10000);   // 30000 Millisecond  = 30 second
    }

}

