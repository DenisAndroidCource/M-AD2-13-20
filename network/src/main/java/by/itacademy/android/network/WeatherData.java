package by.itacademy.android.network;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    private String description;
    @SerializedName("icon")
    private String iconId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
