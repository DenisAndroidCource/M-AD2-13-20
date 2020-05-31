package com.example.maps.repo;

import androidx.lifecycle.LiveData;

import com.example.maps.domain.Photo;

import java.util.List;
import java.util.concurrent.Future;

public interface PhotoRepository {
    LiveData<List<Photo>> getPhotoLiveData();
    Future<List<Photo>> getPhoto();
    LiveData<Photo> getPhoto(long id);
    void saveNewPhoto(final Photo photo);
}
