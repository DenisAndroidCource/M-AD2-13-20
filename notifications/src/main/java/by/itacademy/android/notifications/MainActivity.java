package by.itacademy.android.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * https://developer.android.com/guide/topics/ui/notifiers/notifications?hl=ru
 */
public class MainActivity extends AppCompatActivity {

    private final Map<Integer, View.OnClickListener> VIEW_CLICK_LISTENER_MAP = new HashMap<Integer, View.OnClickListener>() {{
        put(R.id.btnBasicNotification, (view) -> showBasicNotification());
        put(R.id.btnAnotherNotification, (view) -> showDifferentBasicNotification());
        put(R.id.btnUpdateNotification, (view) -> updateBasicNotification());
        put(R.id.btnStyledNotification, (view) -> showStyledNotification());
        put(R.id.btnTapNotification, (view) -> showTapableNotification());
    }};

    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "CHANNEL_ID";
    private int notificationCounter = NOTIFICATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewOnClickListeners();
    }

    private void showBasicNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Basic notification TITLE")
                .setContentText("Basic notification TEXT")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        showNotification(NOTIFICATION_ID, notification);
    }

    private void showDifferentBasicNotification() {
        notificationCounter += 1;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Basic notification TITLE" + notificationCounter)
                .setContentText("Basic notification TEXT" + notificationCounter)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        showNotification(notificationCounter, notification);
    }

    private void updateBasicNotification() {
        final String channelId = "CHANNEL_ID";
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Basic notification TITLE *** CHANGES ***")
                .setContentText("Basic notification TEXT *** CHANGES ***")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        showNotification(NOTIFICATION_ID, notification);
    }

    private void showStyledNotification() {
        String bigText = "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line..." +
                "Much longer text that cannot fit one line...";

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
                .bigText(bigText);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Styled notification TITLE")
                .setContentText("Styled notification TEXT")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(style)
                .build();
        showNotification(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_NAME = "CHANNEL_NAME";
            String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showTapableNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent snoozeIntent = new Intent(this, PendingIntentReceiver.class);
        snoozeIntent.setAction("ACTION_SNOOZE");
        snoozeIntent.putExtra("EXTRA_NOTIFICATION_ID", 0);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Tap notification")
                .setContentText("Tap notification and reopen MainActivtiy")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_snooze_black_24dp, "Snooze", snoozePendingIntent)
                .build();

        showNotification(NOTIFICATION_ID, notification);
    }

    private void showNotification(int notificationId, @NonNull Notification notification) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notification);
        }
    }

    private void initViewOnClickListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            VIEW_CLICK_LISTENER_MAP.forEach(
                    (viewId, onClickListener) -> findViewById(viewId).setOnClickListener(onClickListener)
            );
        } else {
            for (Map.Entry<Integer, View.OnClickListener> entry : VIEW_CLICK_LISTENER_MAP.entrySet()) {
                findViewById(entry.getKey()).setOnClickListener(entry.getValue());
            }
        }
    }

}

