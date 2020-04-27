package by.itacademy.android.network;

import com.google.gson.annotations.SerializedName;

public class WeatherDataMain {
    @SerializedName("temp")
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
