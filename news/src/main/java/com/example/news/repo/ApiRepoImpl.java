package com.example.news.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class ApiRepoImpl implements ApiRepo {

    private Disposable disposable;

    public Observable<List<News>> getNewsList(String locale) {
        NewsApi newsApi = new NewsApi();
        return newsApi.getNewsList(locale);
    }

    public void getFirstNews(String locale, Consumer<List<News>> onSuccess, Consumer<Throwable> onError) {
        NewsApi newsApi = new NewsApi();
        disposable = newsApi.getNewsList(locale)
                .firstElement()
                .subscribe(onSuccess, onError);
    }
}
