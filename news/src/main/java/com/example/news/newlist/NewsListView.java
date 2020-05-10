package com.example.news.newlist;

import com.example.news.common.BaseView;
import com.example.news.repo.News;

import java.util.List;

public interface NewsListView extends BaseView {
    void showNewsList(List<News> news);
    void showError(Throwable throwable);
    void showLoading();
    void hideLoading();
}
