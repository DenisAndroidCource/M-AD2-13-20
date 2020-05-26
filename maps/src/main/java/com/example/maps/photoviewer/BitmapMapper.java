package com.example.maps.photoviewer;

import android.graphics.Bitmap;

import androidx.arch.core.util.Function;

import com.example.maps.domain.Photo;
import com.example.maps.utils.ImageUtils;

public class BitmapMapper implements Function<Photo, Bitmap> {
    @Override
    public Bitmap apply(Photo photo) {
        return ImageUtils.fileToBitmap(photo.getFile());
    }
}
