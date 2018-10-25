package com.example.abhijithsreekar.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("id")
    private String strId;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public Reviews() {}

    public Reviews(String strId, String author, String content, String url, long reviewsId) {
        this.strId = strId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getStrId() {
        return strId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }


    public void setStrId(String strId) {
        this.strId = strId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}