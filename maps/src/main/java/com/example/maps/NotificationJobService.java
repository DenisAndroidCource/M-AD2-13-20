package com.example.maps;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.example.maps.utils.NotificationUtils;

public class NotificationJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        NotificationUtils.showNotification(getApplicationContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
