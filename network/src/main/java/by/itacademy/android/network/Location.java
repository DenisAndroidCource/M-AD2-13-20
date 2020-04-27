package by.itacademy.android.network;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lng;

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
