package com.example.maps.repo;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.maps.database.AppDatabase;
import com.example.maps.database.PhotoDao;
import com.example.maps.database.PhotoEntity;
import com.example.maps.domain.Photo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PhotoRepositoryImpl implements PhotoRepository {

    private PhotoDao photoDao;
    private ExecutorService databaseExecutorService;
    private Function<PhotoEntity, Photo> mapper;

    public PhotoRepositoryImpl(@NonNull final Context context, Function<PhotoEntity, Photo> mapper) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        this.photoDao = appDatabase.getPhotoDao();
        this.databaseExecutorService = appDatabase.getDatabaseExecutorService();
        this.mapper = mapper;
    }

    @Override
    public LiveData<List<Photo>> getPhotoLiveData() {
        return Transformations.map(photoDao.getAllLivaData(), photoEntityList -> photoEntityList.stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public Future<List<Photo>> getPhoto() {
        return databaseExecutorService.submit(() -> photoDao.getAll()
                .stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    @Override
    public LiveData<Photo> getPhoto(long id) {
        return Transformations.map(photoDao.getById(id), photoEntity -> mapper.apply(photoEntity));
    }

    @Override
    public void saveNewPhoto(final Photo photo) {
        databaseExecutorService.execute(() ->
                photoDao.insert(new PhotoEntity(photo.getFile().getAbsolutePath(), photo.getLocation(), photo.getDate()))
        );
    }

    @Override
    public long savePhoto(Photo photo) {
        return photoDao.insert(new PhotoEntity(photo.getFile().getAbsolutePath(), photo.getLocation(), photo.getDate()));
    }

    @Override
    public Cursor getAllPhotosWithCursor() {
        return photoDao.getAllPhotosWithCursor();
    }

    @Override
    public Cursor getAllPhotoWithCursor(long id) {
        return photoDao.getAllPhotoWithCursor(id);
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
