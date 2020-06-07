package com.example.maps.photoviewer;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maps.domain.Photo;
import com.example.maps.domain.PhotoMapper;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.repo.PhotoRepositoryImpl;

public class PhotoViewerViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public PhotoViewerViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(PhotoViewerFragmentViewModel.class)) {
            return (T) new PhotoViewerFragmentViewModel(getPhotoRepository(), getBitmapMapper());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private PhotoRepository getPhotoRepository() {
        return new PhotoRepositoryImpl(context, new PhotoMapper());
    }

    private Function<Photo, PhotoViewState> getBitmapMapper() {
        return new BitmapMapper();
    }
}
