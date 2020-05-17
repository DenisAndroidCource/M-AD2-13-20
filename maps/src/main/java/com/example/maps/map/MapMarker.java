package com.example.maps.map;

import com.google.android.gms.maps.model.MarkerOptions;

public class MapMarker {

    private MarkerOptions markerOptions;

    public MapMarker(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }
}
