package com.example.maps.domain;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.Date;

public class Photo {
    private long id;
    private File file;
    private LatLng location;
    private Date date;

    public Photo(File file, LatLng location, Date date) {
        this.file = file;
        this.location = location;
        this.date = date;
    }

    public Photo(long id, File file, LatLng location, Date date) {
        this.id = id;
        this.file = file;
        this.location = location;
        this.date = date;
    }

    public long getId() {
        return id;
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
