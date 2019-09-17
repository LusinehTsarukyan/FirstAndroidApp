package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    public static ArticlesManager articlesManager = null;
    public static final String apiKey = "6cb66347-dd59-4b6c-be55-731200528471";
    private ContexManager contexManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexManager.setMainContext(this);
        articlesManager = new ArticlesManager(this);
        final String urlString = "https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=" + apiKey;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          ConnectivityManager connectivityManager
                                                  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                          NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                                          if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()){
                                              articlesManager.readFromFile();
                                          }else{
                                              new RequestJsonTask(articlesManager).execute(urlString);
                                          }
                                      }
                                  },
                0, 30000);   // 30000 Millisecond  = 30 second

        // set up the RecyclerView
        RecyclerView horizontalRecyclerView = findViewById(R.id.pinnedArticles);
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(horizontalLinearLayoutManager);

        articlesManager.horizontalRecyclerViewAdapter.setClickListener(new HorizontalRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Article article) {
                ImageView imageView = view.findViewById(R.id.pinnedImg);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("summary", article.getSummary());
                intent.putExtra("id", article.getId());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(), imageView
                        , "img_transition");
                startActivity(intent, option.toBundle());
            }
        });
        horizontalRecyclerView.setAdapter(articlesManager.horizontalRecyclerViewAdapter);


        RecyclerView recyclerView = findViewById(R.id.articles);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstItemVisible != 0 && firstItemVisible % articlesManager.adapter.articleList.size() == 0) {
                    recyclerView.getLayoutManager().scrollToPosition(0);
                }
            }
        });

        articlesManager.adapter.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Article article) {
                ImageView imageView = view.findViewById(R.id.img_activity_1);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("summary", article.getSummary());
                intent.putExtra("id", article.getId());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(), imageView
                        , "img_transition");
                startActivity(intent, option.toBundle());

                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ContexManager.getMainContext()
                            .openFileOutput("titles.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(article.getTitle());
                    articlesManager.fileIsEmpty = false;
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        });
        recyclerView.setAdapter(articlesManager.adapter);
    }
}

