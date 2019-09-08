package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    public static ArticlesKeeper articlesKeeper = null;
    private final String apiKey = "6cb66347-dd59-4b6c-be55-731200528471";
    private MyRecyclerViewAdapter adapter;
    ContexManager contexManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexManager.setMainContext(this);
        articlesKeeper = new ArticlesKeeper(this);

        final String urlString = "https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=" + apiKey;
        new RequestJsonTask(articlesKeeper).execute(urlString);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = articlesKeeper.adapter;
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          new RequestJsonTask(articlesKeeper).execute(urlString);
                                      }
                                  },
                0, 10000);   // 30000 Millisecond  = 30 second
    }

    @Override
    public void onItemClick(View view, int position) {}
}

