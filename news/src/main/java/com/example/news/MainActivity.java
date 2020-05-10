package com.example.news;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.news.newlist.NewsListFragment;
import com.example.news.newsviewer.NewsViewerFragment;
import com.example.news.repo.News;

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnNewsListFragmentAction {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNewsListFragment();
    }

    private void showNewsListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, NewsListFragment.newInstance(), NewsListFragment.FRAGMENT_TAG)
                .commit();
    }

    private void showNewsViewerFragment(News news) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, NewsViewerFragment.newInstance(news), NewsViewerFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onNewsClicked(News news) {
        showNewsViewerFragment(news);
    }
}
