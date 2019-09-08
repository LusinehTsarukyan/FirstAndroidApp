package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = articlesKeeper.adapter;

        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView =  view.findViewById(R.id.img_activity_1);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable)imageView.getDrawable()).getBitmap());
                intent.putExtra("title", ((TextView)view.findViewById(R.id.Title)).getText().toString());
                intent.putExtra("summary", ((TextView)view.findViewById(R.id.summary)).getText().toString());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(),imageView
                       , "img_transition");
                startActivity(intent, option.toBundle());
            }
        });
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

}

