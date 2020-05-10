package com.example.news.newlist;

import com.example.news.repo.ApiRepo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

public class NewsListFragmentPresenterImpl implements NewsListFragmentPresenter {

    private NewsListView view;
    private ApiRepo apiRepo;

    private Disposable disposable;

    NewsListFragmentPresenterImpl(ApiRepo apiRepo) {
        this.apiRepo = apiRepo;
    }

    @Override
    public void fetchNewsList() {
        view.showLoading();
        disposable = apiRepo.getNewsList("us")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        news -> {
                            view.hideLoading();
                            view.showNewsList(news);
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showError(throwable);
                        });
    }

    @Override
    public void setView(NewsListView view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        view = null;
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
