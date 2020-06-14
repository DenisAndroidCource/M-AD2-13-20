package com.example.maps;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.maps.utils.NotificationUtils;

public class NotificationWorker extends Worker {

    private Data data;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        data = workerParams.getInputData();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("NotificationWorker", Thread.currentThread().getName());
        NotificationUtils.showNotification(getApplicationContext());

        Data data = new Data.Builder()
                .putString("NotificationWorker2_TITLE", "NotificationWorker2")
                .build();

        return Worker.Result.success(data);
    }
}
