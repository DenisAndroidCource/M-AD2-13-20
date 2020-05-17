package com.example.maps.database;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.Locale;

import static java.lang.Double.parseDouble;
import static java.lang.String.format;

public class Converters {

    private static final Locale DEFAULT_LOCALE = Locale.UK;
    private static final String LOCATION_STRING_FORMAT_SEPARATOR = ";";
    private static final String LOCATION_STRING_FORMAT = "%f;%f";

    @TypeConverter
    public static String locationToString(LatLng location) {
        return format(DEFAULT_LOCALE, LOCATION_STRING_FORMAT, location.latitude, location.longitude);
    }

    @TypeConverter
    public static LatLng stringToLocation(String locationText) {
        String[] coordsText = locationText.split(LOCATION_STRING_FORMAT_SEPARATOR);
        return new LatLng(parseDouble(coordsText[0]), parseDouble(coordsText[1]));
    }

    @TypeConverter
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date longToDate(long millisecond) {
        return new Date(millisecond);
    }
}
