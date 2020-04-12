package by.itacademy.android.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class SimpleIntentService extends IntentService {

    public SimpleIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }
}
