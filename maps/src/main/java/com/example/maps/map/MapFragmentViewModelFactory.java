package com.example.maps.map;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maps.domain.Photo;
import com.example.maps.domain.PhotoMapper;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.repo.PhotoRepositoryImpl;
import com.example.maps.utils.LocationController;

import java.util.List;

public class MapFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public MapFragmentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(MapFragmentViewModel.class)) {
            return (T) new MapFragmentViewModel(getPhotoRepository(), getLocationController(), getPhotoMapper());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private PhotoRepository getPhotoRepository() {
        return new PhotoRepositoryImpl(context, new PhotoMapper());
    }

    private LocationController getLocationController() {
        return new LocationController(context);
    }

    private Function<List<Photo>, List<MapMarker>> getPhotoMapper() {
        return new MapMarkerMapper();
    }
}
