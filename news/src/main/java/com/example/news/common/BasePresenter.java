package com.example.news.common;

public interface BasePresenter<TView extends BaseView> {
    void setView(TView view);

    void removeView();
}
