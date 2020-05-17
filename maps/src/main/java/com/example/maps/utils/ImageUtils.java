package com.example.maps.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.File;

public class ImageUtils {

    public static Bitmap fileToBitmap(String filePath) {
        return fileToBitmap(new File(filePath));
    }

    public static Bitmap fileToBitmap(File imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

    public static BitmapDescriptor prepareIconForMarker(File imageFile){
        Bitmap bitmap = fileToBitmap(imageFile);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        return BitmapDescriptorFactory.fromBitmap(scaledBitmap);
    }

    private ImageUtils() {
    }
}
