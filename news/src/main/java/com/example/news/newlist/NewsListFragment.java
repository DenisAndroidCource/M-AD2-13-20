package com.example.news.newlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.repo.ApiRepoImpl;
import com.example.news.repo.News;

import java.util.List;

public class NewsListFragment extends Fragment implements NewsListView {

    public interface OnNewsListFragmentAction {
        void onNewsClicked(News news);
    }

    public static String FRAGMENT_TAG = "NewsListFragment";

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    private NewsListFragmentPresenter presenter = new NewsListFragmentPresenterImpl(new ApiRepoImpl());
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private OnNewsListFragmentAction onNewsListFragmentAction;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsListFragmentAction) {
            onNewsListFragmentAction = (OnNewsListFragmentAction) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(new NewsListAdapter(news -> {
            if (onNewsListFragmentAction != null) {
                onNewsListFragmentAction.onNewsClicked(news);
            }
        }));

        progressBar = view.findViewById(R.id.progress);
        presenter.setView(this);
        presenter.fetchNewsList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onNewsListFragmentAction = null;
        presenter.removeView();
    }

    @Override
    public void showNewsList(List<News> news) {
        NewsListAdapter adapter = (NewsListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItems(news);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(getContext(), "Sorry something is wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
