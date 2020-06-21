package com.example.maps;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maps.domain.Photo;
import com.example.maps.domain.PhotoMapper;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.repo.PhotoRepositoryImpl;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class PhotoContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.maps.PHOTO_PROVIDER";

    private static final String CONTENT_ID = "CONTENT_ID";
    private static final String CONTENT_FILE = "CONTENT_FILE";
    private static final String CONTENT_LOCATION_LAT = "CONTENT_LOCATION_LAT";
    private static final String CONTENT_LOCATION_LON = "CONTENT_LOCATION_LON";
    private static final String CONTENT_DATE = "CONTENT_DATE";

    private static final UriMatcher uriMatcher;

    private static final int URI_KEY_PHOTO_ALL = 1;
    private static final int URI_KEY_PHOTO = 2;
    private static final int URI_KEY_PHOTO_COMMON = 3;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "PHOTO/ALL", URI_KEY_PHOTO_ALL);
        uriMatcher.addURI(AUTHORITY, "PHOTO/#", URI_KEY_PHOTO);
        uriMatcher.addURI(AUTHORITY, "PHOTO", URI_KEY_PHOTO_COMMON);
    }

    private PhotoRepository photoRepository;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            photoRepository = new PhotoRepositoryImpl(context, new PhotoMapper());
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_KEY_PHOTO_ALL:
                return photoRepository.getAllPhotosWithCursor();
            case URI_KEY_PHOTO:
                return photoRepository.getAllPhotoWithCursor(Long.parseLong(uri.getLastPathSegment()));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "application/data";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (isContentValues(values, CONTENT_ID, CONTENT_FILE, CONTENT_LOCATION_LAT, CONTENT_LOCATION_LON, CONTENT_DATE)) {
            Photo photo = getPhoto(values);
            return ContentUris.withAppendedId(uri, photoRepository.savePhoto(photo));
        }
        return null;
    }

    private Photo getPhoto(ContentValues values) {
        long id = values.getAsLong(CONTENT_ID);
        File file = new File(values.getAsString(CONTENT_FILE));
        LatLng latLng = new LatLng(values.getAsDouble(CONTENT_LOCATION_LAT), values.getAsDouble(CONTENT_LOCATION_LON));
        Date date = new Date(values.getAsLong(CONTENT_DATE));
        return new Photo(id, file, latLng, date);
    }

    private boolean isContentValues(@Nullable ContentValues values, String... keys) {
        return values != null && Arrays.stream(keys).allMatch(values::containsKey);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uriMatcher.match(uri) == URI_KEY_PHOTO) {
            return photoRepository.delete(Long.parseLong(uri.getLastPathSegment()));
        }
        return -1;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
