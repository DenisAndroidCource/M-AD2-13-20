package com.example.maps.repo;

import androidx.lifecycle.LiveData;

import com.example.maps.domain.Photo;

import java.util.List;

public interface PhotoRepository {
    LiveData<List<Photo>> getPhoto();
    LiveData<Photo> getPhoto(long id);
    void saveNewPhoto(final Photo photo);
}
