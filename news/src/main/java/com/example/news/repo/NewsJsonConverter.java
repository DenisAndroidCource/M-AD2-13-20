package com.example.news.repo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsJsonConverter {

    public static List<News> toNews(String json) {
        ArrayList<News> newsList = new ArrayList<News>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonArticle = jsonArray.getJSONObject(i);

                News.Builder builder = new News.Builder()
                        .setTitle(jsonArticle.getString("title"))
                        .setDescription(jsonArticle.getString("description"))
                        .setUrl(jsonArticle.getString("url"))
                        .setImageUrl(jsonArticle.getString("urlToImage"));

                newsList.add(builder.build());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }


}
