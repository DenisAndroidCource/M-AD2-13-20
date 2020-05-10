package com.example.news.newlist;

import com.example.news.common.BasePresenter;

public interface NewsListFragmentPresenter extends BasePresenter<NewsListView> {
    void fetchNewsList();
}
