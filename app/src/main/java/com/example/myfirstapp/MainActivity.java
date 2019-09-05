package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static TextView titleData;
    public static TextView pillarData;
    public static ImageView imageData;
    public static ArticlesKeeper articlesKeeper = null;
    private final String apiKey = "6cb66347-dd59-4b6c-be55-731200528471";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageData = (ImageView) findViewById(R.id.img_activity_1);
        imageData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imageData, ViewCompat.getTransitionName(imageData));
                startActivity(intent, option.toBundle());
            }

        });

        articlesKeeper = new ArticlesKeeper(this);
        titleData = (TextView) findViewById(R.id.Title);
        pillarData = (TextView) findViewById(R.id.Pillar);


        final String urlString = "https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=" + apiKey; // + "&show-fields=thumbnail";

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          new RequestJsonTask(articlesKeeper).execute(urlString);
                                          //testing update
//                                          TestJsonTask task = new TestJsonTask(articlesKeeper);
//                                          task.execute();
//                                          task.testCount++;
                                          //test end
                                      }
                                  },
                0, 10000);   // 30000 Millisecond  = 30 second

    }
}

