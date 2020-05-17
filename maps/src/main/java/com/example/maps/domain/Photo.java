package com.example.maps.domain;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.Date;

public class Photo {
    private File file;
    private LatLng location;
    private Date date;

    public Photo(File file, LatLng location, Date date) {
        this.file = file;
        this.location = location;
        this.date = date;
    }

    public File getFile() {
        return file;
    }

    public LatLng getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }
}
