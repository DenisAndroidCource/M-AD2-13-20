package com.example.maps.map;

import androidx.arch.core.util.Function;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maps.domain.Photo;
import com.example.maps.repo.PhotoRepository;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.example.maps.utils.ImageUtils.prepareIconForMarker;

public class MapFragmentViewModel extends ViewModel {

    private PhotoRepository photoRepository;

    public MapFragmentViewModel(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public void fetchPhoto(){
        Transformations.map(photoRepository.getPhoto(), new Function<List<Photo>, List<MapMarker>>() {
            @Override
            public List<MapMarker> apply(List<Photo> photoList) {
                photoList.stream()
                        .map(photo -> {
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(photo.getLocation())
                                    .draggable(false)
                                    .icon(prepareIconForMarker(photo.getFile()));
                            return new MapMarker(markerOptions);
                        });
                return null;
            }
        };
    }

    public void saveNewPhoto(){

    }
}
