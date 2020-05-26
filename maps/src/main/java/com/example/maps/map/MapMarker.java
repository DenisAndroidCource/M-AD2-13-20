package com.example.maps.map;

import com.google.android.gms.maps.model.MarkerOptions;

public class MapMarker {

    private long tag;
    private MarkerOptions markerOptions;

    MapMarker(long tag, MarkerOptions markerOptions) {
        this.tag = tag;
        this.markerOptions = markerOptions;
    }

    public long getTag() {
        return tag;
    }

    MarkerOptions getMarkerOptions() {
        return markerOptions;
    }
}
