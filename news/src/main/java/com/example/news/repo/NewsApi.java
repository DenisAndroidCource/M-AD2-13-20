package com.example.news.repo;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {

    private static String API_KEY = "fe27628816ba4ca5b23fe932cf36e26e";
    private static String GET_NEWS_URL = "https://newsapi.org/v2/top-headlines?country=%s&apiKey=%s";

    private OkHttpClient okHttpClient = new OkHttpClient();

    public Observable<List<News>> getNewsList(String locale) {
        String url = String.format(GET_NEWS_URL, locale, API_KEY);

        final Request request = new Request.Builder()
                .url(url)
                .build();


        return Observable.create((ObservableOnSubscribe<Response>) emitter -> {
            try {
                emitter.onNext(okHttpClient.newCall(request).execute());
            } catch (IOException e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .map(Response::body)
                .map(responseBody -> NewsJsonConverter.toNews(responseBody.string()));
    }
}
