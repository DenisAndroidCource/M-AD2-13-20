package com.example.news.newsviewer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.repo.News;

public class NewsViewerViewModel extends ViewModel {

    private MutableLiveData<String> urlLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public LiveData<String> getUrlLiveData() {
        return urlLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void setNewsData(News newsData) {
        urlLiveData.setValue(newsData.getUrl());
    }

    public void setLoadingState(boolean isShow){
        loadingLiveData.setValue(isShow);
    }
}
