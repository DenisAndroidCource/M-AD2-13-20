package com.example.maps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.maps.MainActivity;
import com.example.maps.R;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.example.maps.MainActivity.EXTRA_OPEN_PHOTO_ACTION;
import static java.util.Arrays.stream;

public class PhotoListWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        stream(appWidgetIds).forEach(appWidgetId -> setList(context, appWidgetManager, appWidgetId));
    }

    private void setList(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent listAdapterIntent = new Intent(context, PhotoListWidgetService.class);
        listAdapterIntent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_photo_list);
        remoteViews.setRemoteAdapter(R.id.photoList, listAdapterIntent);

        setClickListener(context, remoteViews);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.photoList);
    }

    private void setClickListener(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(EXTRA_OPEN_PHOTO_ACTION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setPendingIntentTemplate(R.id.photoList, pendingIntent);
    }
}
