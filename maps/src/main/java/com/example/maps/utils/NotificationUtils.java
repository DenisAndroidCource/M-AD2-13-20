package com.example.maps.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.maps.MainActivity;
import com.example.maps.R;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {

    public static final String APP_NOTIFICATION_CHANNEL_ID = "APP_NOTIFICATION_CHANNEL_ID";

    public static void createAppNotificationChannel(@NonNull NotificationManager notificationManager, String channelName, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(APP_NOTIFICATION_CHANNEL_ID, channelName, IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void showNotification(@NonNull Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, APP_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_photo_camera)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notificationText))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 500, 1000})
                .setContentIntent(getMainActivityIntent(context));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(5, builder.build());
        }
    }

    private static PendingIntent getMainActivityIntent(@NonNull Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private NotificationUtils() {
    }
}
