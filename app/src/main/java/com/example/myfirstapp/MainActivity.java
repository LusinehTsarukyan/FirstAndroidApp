package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          data = (TextView) findViewById(R.id.BackgroundTask);
                                          Log.println(Log.DEBUG, "Successfully!", "Getting articles.");
                                          BackgroundTask process = new BackgroundTask();
                                          process.execute();
                                      }
                                  },
                0, 30000);   // 30000 Millisecond  = 30 second

    }
}
