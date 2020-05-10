package com.example.news.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public interface ApiRepo {
    Observable<List<News>> getNewsList(String locale);
    void getFirstNews(String locale, Consumer<List<News>> onSuccess, Consumer<Throwable> onError);
}
