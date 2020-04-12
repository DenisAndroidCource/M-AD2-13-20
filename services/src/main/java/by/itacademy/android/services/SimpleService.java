package by.itacademy.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public class SimpleService extends Service {

    private static final String TAG = "SimpleService";
    public static final String EXTRA_DATA = "EXTRA_DATA";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "startId " + startId);
        Log.d(TAG, "EXTRA_DATA " + intent.getStringExtra(EXTRA_DATA));
        someJob(startId);
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SimpleServiceBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public String getResult(){
        return "Simple service result";
    }

    private void someJob(final int startId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "Started " + startId);
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Stopped " + startId);
//                stopSelf(startId);
            }
        });
        thread.start();
    }

    public class SimpleServiceBinder extends Binder {
        public SimpleService getService() {
            return SimpleService.this;
        }
    }
}
