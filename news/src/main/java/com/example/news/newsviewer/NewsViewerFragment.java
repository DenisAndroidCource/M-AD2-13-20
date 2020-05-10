package com.example.news.newsviewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.news.R;
import com.example.news.repo.News;

public class NewsViewerFragment extends Fragment {

    private static final String EXTRA_NEWS_KEY = "EXTRA_NEWS_KEY";
    public static String FRAGMENT_TAG = "NewsViewerFragment";

    public static NewsViewerFragment newInstance(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_NEWS_KEY, news);

        NewsViewerFragment fragment = new NewsViewerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private NewsViewerViewModel viewerViewModel;
    private WebView webView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();

        progressBar = view.findViewById(R.id.progress);

        webView = view.findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                viewerViewModel.setLoadingState(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                viewerViewModel.setLoadingState(false);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);

        showNews();
    }

    private void initViewModel() {
        viewerViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(NewsViewerViewModel.class);
        viewerViewModel.getUrlLiveData().observe(getViewLifecycleOwner(), url -> webView.loadUrl(url));
        viewerViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isShow -> progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE));
    }

    private void showNews() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getSerializable(EXTRA_NEWS_KEY) != null) {
            viewerViewModel.setNewsData((News) bundle.getSerializable(EXTRA_NEWS_KEY));
        }
    }
}
