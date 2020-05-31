package com.example.maps.photoviewer;

import android.graphics.Bitmap;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maps.domain.Photo;
import com.example.maps.repo.PhotoRepository;

public class PhotoViewerFragmentViewModel extends ViewModel {

    private PhotoRepository photoRepository;
    private Function<Photo, Bitmap> mapper;

    public PhotoViewerFragmentViewModel(PhotoRepository photoRepository, Function<Photo, Bitmap> mapper) {
        this.photoRepository = photoRepository;
        this.mapper = mapper;
    }

    LiveData<Bitmap> fetchPhoto(long id) {
        return Transformations.map(photoRepository.getPhoto(id), mapper);
    }
}
