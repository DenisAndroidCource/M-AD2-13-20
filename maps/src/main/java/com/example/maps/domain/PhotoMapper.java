package com.example.maps.domain;

import com.example.maps.database.PhotoEntity;

import java.io.File;
import java.util.function.Function;

public class PhotoMapper implements Function<PhotoEntity, Photo> {
    @Override
    public Photo apply(PhotoEntity photoEntity) {
        return new Photo(photoEntity.getId(), new File(photoEntity.getFilePath()), photoEntity.getLocation(), photoEntity.getDate());
    }
}
