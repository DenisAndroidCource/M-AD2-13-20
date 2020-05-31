package com.example.maps.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.maps.domain.PhotoMapper;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.repo.PhotoRepositoryImpl;

public class PhotoListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Context context = getApplicationContext();
        PhotoRepository photoRepository = new PhotoRepositoryImpl(context, new PhotoMapper());
        return new PhotoListWidgetRemoteViewFactory(context.getPackageName(), photoRepository);
    }
}
