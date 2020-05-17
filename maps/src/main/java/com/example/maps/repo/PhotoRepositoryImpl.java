package com.example.maps.repo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.maps.database.AppDatabase;
import com.example.maps.database.PhotoDao;
import com.example.maps.database.PhotoEntity;
import com.example.maps.domain.Photo;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class PhotoRepositoryImpl implements PhotoRepository{

    private PhotoDao photoDao;
    private ExecutorService databaseExecutorService;
    private LiveData<List<PhotoEntity>> photoEntityListLiveData;

    public PhotoRepositoryImpl(@NonNull final Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        this.photoDao = appDatabase.getPhotoDao();
        this.databaseExecutorService = appDatabase.getDatabaseExecutorService();
    }

    @Override
    public LiveData<List<Photo>> getPhoto() {
        if (photoEntityListLiveData == null) {
            photoEntityListLiveData = photoDao.getAll();
        }

        return Transformations.map(photoEntityListLiveData, photoEntityList -> photoEntityList.stream()
                .map(photoEntity ->
                        new Photo(new File(photoEntity.getFilePath()), photoEntity.getLocation(), photoEntity.getDate()))
                .collect(Collectors.toList()));
    }

    @Override
    public void saveNewPhoto(final Photo photo) {
        databaseExecutorService.execute(() ->
                photoDao.insert(new PhotoEntity(photo.getFile().getAbsolutePath(), photo.getLocation(), photo.getDate()))
        );
    }
}
