package com.example.maps.photoviewer;

import androidx.arch.core.util.Function;

import com.example.maps.domain.Photo;
import com.example.maps.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapMapper implements Function<Photo, PhotoViewState> {
    @Override
    public PhotoViewState apply(Photo photo) {
        return new PhotoViewState(ImageUtils.fileToBitmap(photo.getFile()), getDateText(photo.getDate()));
    }

    private String getDateText(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.YYYY");
        return simpleDateFormat.format(date);
    }
}
