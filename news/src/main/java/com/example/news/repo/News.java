package com.example.news.repo;

import java.io.Serializable;

public class News implements Serializable {
    private String title;
    private String description;
    private String url;
    private String imageUrl;

    private News(String title, String description, String url, String imageUrl) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    static class Builder {
        private String title;
        private String description;
        private String url;
        private String imageUrl;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public News build() {
            return new News(title, description, url, imageUrl);
        }
    }

}
