package by.itacademy.android.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("coord")
    private Location location;
    @SerializedName("weather")
    private List<WeatherData> weatherData;
    @SerializedName("main")
    private WeatherDataMain weatherDataMain;
    private String name;

    public Location getLocation() {
        return location;
    }

    public List<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(List<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }

    public WeatherDataMain getWeatherDataMain() {
        return weatherDataMain;
    }

    public void setWeatherDataMain(WeatherDataMain weatherDataMain) {
        this.weatherDataMain = weatherDataMain;
    }
}
