package by.itacademy.android.async;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class LooperThread extends Thread {

    private Handler handler;

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);


                getLooper().quit();
            }
        };

        Looper.loop();
    }
}
