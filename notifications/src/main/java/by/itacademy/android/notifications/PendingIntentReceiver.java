package by.itacademy.android.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PendingIntentReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "PendingIntentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive");
        Log.d(LOG_TAG, "action = " + intent.getAction());
        Log.d(LOG_TAG, "extra = " + intent.getIntExtra("EXTRA_NOTIFICATION_ID", -1));
    }
}
