package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static TextView data;
    public static ArticlesKeeper articlesKeeper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView)findViewById(R.drawable.mayi);
        iv.setImageResource(R.drawable.mayi);

        articlesKeeper = new ArticlesKeeper();
        data = (TextView) findViewById(R.id.BackgroundTask);

        final String urlString = "https://content.guardianapis.com/search?q=article&show-elements=image&show-blocks=all&api-key=6cb66347-dd59-4b6c-be55-731200528471";

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

