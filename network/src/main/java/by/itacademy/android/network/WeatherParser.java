package by.itacademy.android.network;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherParser {

    private String jsonData;

    public WeatherParser(String jsonData) {
        this.jsonData = jsonData;
    }

//    public Weather parseData() throws JSONException {
//        JSONObject jsonObject = new JSONObject(jsonData);
//        JSONObject coordsJsonObj = jsonObject.getJSONObject("coord");
//        JSONObject weatherJsonObj = jsonObject.getJSONArray("weather").getJSONObject(0);
//
//        return new Weather.Builder()
//                .setLocation(new Location(coordsJsonObj.getDouble("lat"), coordsJsonObj.getDouble("lon")))
//                .setDescription(weatherJsonObj.getString("description"))
//                .setIconId(weatherJsonObj.getString("icon"))
//                .setTemperature(jsonObject.getJSONObject("main").getDouble("temp"))
//                .build();
//    }
}
