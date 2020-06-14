package com.example.maps;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.maps.utils.NotificationUtils;

public class NotificationWorker2 extends Worker {

    private Data data;

    public NotificationWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        data = workerParams.getInputData();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("NotificationWorker", Thread.currentThread().getName());
        Log.d("NotificationWorker", data.getString("NotificationWorker2_TITLE"));
        NotificationUtils.showNotification(getApplicationContext());

        return Result.success();
    }
}
