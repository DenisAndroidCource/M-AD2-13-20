package com.example.maps;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.maps.alarm.AlarmReceiver;
import com.example.maps.map.MapFragment;
import com.example.maps.photoviewer.PhotoViewerFragment;
import com.example.maps.utils.NotificationUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MapFragment.MapFragmentActionListener {

    public static final String EXTRA_OPEN_PHOTO_ID = "EXTRA_OPEN_PHOTO_ID";
    public static final String EXTRA_OPEN_PHOTO_ACTION = "com.example.maps.OPEN_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openMapFragment();
        onNewIntentAction(getIntent());

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            NotificationUtils.createAppNotificationChannel(notificationManager,
                    getString(R.string.notificationChannelName), getString(R.string.notificationChannelDescription));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onNewIntentAction(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        scheduleAlarm();
        callWorkers();
    }

    private void openMapFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, MapFragment.getInstance(), MapFragment.TAG)
                .commit();
    }

    private void openPhotoViewerFragment(long photoId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PhotoViewerFragment fragment = (PhotoViewerFragment) fragmentManager.findFragmentByTag(PhotoViewerFragment.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, PhotoViewerFragment.newInstance(photoId), PhotoViewerFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void scheduleAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 5000, pendingIntent);
            Log.d("ALARM", "setInexactRepeating");
        }
    }

    @Override
    public void onPhotoClickAction(long photoId) {
        openPhotoViewerFragment(photoId);
    }

    private void onNewIntentAction(@Nullable Intent intent) {
        if (intent != null && intent.getAction() != null && hasOpenPhotoIntent(intent)) {
            openPhotoViewerFragment(intent.getLongExtra(EXTRA_OPEN_PHOTO_ID, 0));
        }
    }

    private boolean hasOpenPhotoIntent(Intent intent) {
        return intent.getAction().equals(EXTRA_OPEN_PHOTO_ACTION) && intent.hasExtra(EXTRA_OPEN_PHOTO_ID);
    }

    private void scheduleJob() {
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("EXTRA_TITLE", " Notification TITLE");

        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(1, componentName)
                .setRequiresDeviceIdle(false)
//                .setPeriodic(1000 * 5)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//                .setPeriodic(1000 * 60 * 60 * 24)
//                .setExtras(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setRequiresBatteryNotLow(true);
        }

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }

    private void callWorkers() {
        Data data = new Data.Builder()
                .putString("TITLE_MESSAGE", "MESSAGE")
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();
        OneTimeWorkRequest request2 = new OneTimeWorkRequest.Builder(NotificationWorker2.class).build();

        WorkManager.getInstance(this)
                .beginWith(request)
                .then(request2)
                .enqueue();
    }
}
