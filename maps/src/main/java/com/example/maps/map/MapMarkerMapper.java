package com.example.maps.map;

import androidx.arch.core.util.Function;

import com.example.maps.domain.Photo;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.maps.utils.ImageUtils.prepareIconForMarker;

public class MapMarkerMapper implements Function<List<Photo>, List<MapMarker>> {
    @Override
    public List<MapMarker> apply(List<Photo> photoList) {
        return photoList.stream()
                .map(photo -> {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(photo.getLocation())
                            .draggable(false)
                            .icon(prepareIconForMarker(photo.getFile()));
                    return new MapMarker(photo.getId(), markerOptions);
                })
                .collect(Collectors.toList());
    }
}
