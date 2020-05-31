package com.example.maps.map;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maps.domain.Photo;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.utils.LocationController;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.Date;
import java.util.List;

class MapFragmentViewModel extends ViewModel {

    private PhotoRepository photoRepository;
    private Function<List<Photo>, List<MapMarker>> mapper;
    private LocationController locationController;
    private MutableLiveData<LatLng> latLngMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> photoIdLiveData = new MutableLiveData<>();

    MapFragmentViewModel(PhotoRepository photoRepository, LocationController locationController,
                                Function<List<Photo>, List<MapMarker>> mapper) {
        this.photoRepository = photoRepository;
        this.locationController = locationController;
        this.mapper = mapper;
    }

    void openPhoto(long id) {
        photoIdLiveData.setValue(id);
    }

    void fetchUserLocation() {
        latLngMutableLiveData.setValue(locationController.getUserLocation());
    }

    public LiveData<Long> getPhotoIdLiveData() {
        return photoIdLiveData;
    }

    LiveData<LatLng> getLatLngMutableLiveData() {
        return latLngMutableLiveData;
    }

    LiveData<List<MapMarker>> fetchPhoto() {
        return Transformations.map(photoRepository.getPhotoLiveData(), mapper);
    }

    void savePhoto(File photoFile) {
        Photo photo = new Photo(photoFile, locationController.getUserLocation(), new Date());
        photoRepository.saveNewPhoto(photo);
    }
}
