package by.itacademy.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Приемник, принимающий касомные события
 *
 * https://developer.android.com/guide/components/broadcasts
 */
public class CustomBroadcastReceiver extends BroadcastReceiver {

    public static final String CUSTOM_ACTION = "com.example.components.CustomBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String receivedAction = intent.getAction();
        if (CUSTOM_ACTION.equals(receivedAction)) {
            Toast.makeText(context, "CustomBroadcastReceiver -> CUSTOM_ACTION", Toast.LENGTH_SHORT).show();
        }
    }
}
