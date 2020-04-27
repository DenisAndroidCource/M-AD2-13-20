package by.itacademy.android.network;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "3491520b1da85a557a8b092551c46a2d";
    private static final String CITY = "Minsk";

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    private OkHttpClient okHttpClient = new OkHttpClient();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("api.openweathermap.org")
            .build();

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
//        getWeather2();
//        getWeather1();
    }

    private void getWeather3() {
        retrofit.create(WeatherApi.class).getWeather("Minsk", API_KEY).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NotNull Call<Weather> call, @NotNull Response<Weather> response) {

            }

            @Override
            public void onFailure(@NotNull Call<Weather> call, @NotNull Throwable t) {

            }
        });
    }

//    private void getWeather2() {
//        String url = String.format(URL, CITY, API_KEY);
//
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful() && response.body() != null) {
//                    String json = response.body().string();
//
//                    Type type = new TypeToken<Weather>() {
//                    }.getType();
//                    Weather weather = new Gson().fromJson(json, type);
//                    textView.post(() -> textView.setText(weather.getWeatherData().get(0).getDescription()));
//                }
//            }
//        });
//    }

//    private void getWeather1() {
//        final String url = String.format(URL, CITY, API_KEY);
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                return okHttpClient.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }).thenApply(response -> {
//            if (response != null && response.isSuccessful()) {
//                try {
//                    return response.body().string();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }).thenApply(response -> {
//            if (response != null) {
//                try {
//                    return new WeatherParser(response).parseData();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }).thenAcceptAsync(weather -> {
//            if (weather != null) {
//                textView.setText(weather.getDescription());
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
}
