package com.example.maps.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

@Entity(tableName = "photo")
@TypeConverters(value = {Converters.class})
public class PhotoEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String filePath;
    private LatLng location;
    private Date date;

    public PhotoEntity(String filePath, LatLng location, Date date) {
        this.filePath = filePath;
        this.location = location;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
