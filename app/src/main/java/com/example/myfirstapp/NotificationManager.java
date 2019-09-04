package com.example.myfirstapp;
//
//import android.app.Activity;
//import android.app.NotificationChannel;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.os.Build;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//public class NotificationManager extends AppCompatActivity {
//    private final String CHANNEL_ID = "personal_notifications";
//    private final int NOTIFICATION_ID = 001;
//    //private static MainActivity activity = new MainActivity();
//
//    NotificationManager() {
//    }
//
//    public void displayNotification(int count, Context context) {
//        createNotificationChannel();
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID);
//        builder.setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle(count + " new articles available!")
//                .setContentText("Tap to open.")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setVibrate(new long[]{
//                        500,
//                        500,
//                        500,
//                        500
//                });
//
//        //This intent will be fired when the notification is tapped
//        Intent intent = new Intent(activity, NotificationManager.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 1001, intent, 0);
//        //Following will set the tap action
//        builder.setContentIntent(pendingIntent);
//        builder.setAutoCancel(true);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(activity);
//        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
//    }
//
//    public void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Personal Notifications";
//            String description = "Include all the personal notifications";
//            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
//
//            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//
//            notificationChannel.setDescription(description);
//
//            //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            //notificationManager.createNotificationChannel(notificationChannel);
//
//        }
//    }
//}
