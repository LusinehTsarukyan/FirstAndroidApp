package com.example.myfirstapp;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationManager extends AppCompatActivity {
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    private Context context = null;
    private static boolean isFirstTime = true;

    NotificationManager(Context context) {
        this.context = context;
    }

    public void displayNotification(int count) {
        if (!isFirstTime) {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText("Tap to open.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setVibrate(new long[]{
                            500,
                            500,
                            500,
                            500
                    });
            if (count > 1) {
                builder.setContentTitle("See " + count + " newest articles!");

            } else if (count == 1) {
                builder.setContentTitle("New article available!");
            }

            //This intent will be fired when the notification is tapped
            Intent intent = new Intent(context, NotificationManager.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1001, intent, 0);
            //Following will set the tap action
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
        }else {
            isFirstTime = false;
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notifications";
            String description = "Include all the personal notifications";
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

        }
    }
}
