package com.example.abhijithsreekar.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

public class Reviews {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public Reviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}