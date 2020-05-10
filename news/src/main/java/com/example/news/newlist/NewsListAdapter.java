package com.example.news.newlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.repo.News;

import java.util.ArrayList;
import java.util.List;

public class    NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder> {

    interface NewsItemActionListener {
        void onItemClick(News news);
    }

    private ArrayList<News> news = new ArrayList<>();
    private NewsItemActionListener newsItemActionListener;

    public NewsListAdapter(@NonNull NewsItemActionListener newsItemActionListener) {
        this.newsItemActionListener = newsItemActionListener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsItemViewHolder(view, newsItemActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news != null ? news.size() : 0;
    }

    public void setItems(@NonNull List<News> news) {
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    static class NewsItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView previewImageView;
        private NewsItemActionListener newsItemActionListener;

        NewsItemViewHolder(@NonNull View itemView, @NonNull NewsItemActionListener newsItemActionListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            descriptionTextView = itemView.findViewById(R.id.textDescription);
            previewImageView = itemView.findViewById(R.id.imgPreview);
            this.newsItemActionListener = newsItemActionListener;
        }

        void bind(News news) {
            titleTextView.setText(news.getTitle());
            descriptionTextView.setText(news.getDescription());

            Glide.with(itemView.getContext())
                    .load(news.getImageUrl())
                    .into(previewImageView);

            itemView.setOnClickListener(v -> newsItemActionListener.onItemClick(news));
        }
    }
}
