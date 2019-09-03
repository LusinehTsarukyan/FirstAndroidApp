package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static TextView data;
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer();
        //File path = getApplicationContext().getFilesDir();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          data = (TextView) findViewById(R.id.BackgroundTask);
                                          Log.println(Log.DEBUG, "Successfully!", "Getting articles.");
                                          //show-blocks=all& for all fields, like summary, image...
                                          BackgroundTask myTask = new BackgroundTask("https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=6cb66347-dd59-4b6c-be55-731200528471");
                                          //Testing update
//                                          TestBackgroundTask myTask = new TestBackgroundTask("https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=6cb66347-dd59-4b6c-be55-731200528471");
//                                          myTask.testCount++;
                                          //update test end

                                          myTask.execute();
                                          if (myTask.isUpdated > 0) {
                                              displayNotification(myTask.isUpdated);
                                              myTask.isUpdated = 0;
                                          }
                                      }
                                  },
                0, 30000);   // 30000 Millisecond  = 30 second

    }

    public void displayNotification(int count) {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(count + " new articles available!");
        builder.setContentText("Tap to open.");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setVibrate(new long[]{
                500,
                500,
                500,
                500
        });

        //This intent will be fired when the notification is tapped
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1001, intent, 0);
        //Following will set the tap action
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}
