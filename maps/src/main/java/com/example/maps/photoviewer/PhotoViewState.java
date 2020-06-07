package com.example.maps.photoviewer;

import android.graphics.Bitmap;

public class PhotoViewState {
    private Bitmap bitmap;
    private String dateText;

    public PhotoViewState(Bitmap bitmap, String dateText) {
        this.bitmap = bitmap;
        this.dateText = dateText;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDateText() {
        return dateText;
    }
}
