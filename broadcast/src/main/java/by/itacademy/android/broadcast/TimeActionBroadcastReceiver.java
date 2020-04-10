package by.itacademy.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Приемник широковещательных сообщений, который реагирует на системные события,
 * которые сигнализируют об изменении настроек времени
 *
 * https://developer.android.com/guide/components/broadcasts
 */
public class TimeActionBroadcastReceiver extends BroadcastReceiver {

    // События, на которые реагирует наш ресивер
    private static final String ACTION_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;
    private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;

    @Override
    public void onReceive(Context context, Intent intent) {
        String receivedAction = intent.getAction();
        if (ACTION_TIME_CHANGED.equals(receivedAction)) {
            Toast.makeText(context, "TimeActionBroadcastReceiver -> ACTION_TIME_CHANGED", Toast.LENGTH_SHORT).show();
        }

        if (ACTION_TIMEZONE_CHANGED.equals(receivedAction)) {
            Toast.makeText(context, "TimeActionBroadcastReceiver -> ACTION_TIMEZONE_CHANGED", Toast.LENGTH_SHORT).show();
        }
    }


}
