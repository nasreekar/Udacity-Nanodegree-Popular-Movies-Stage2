package com.example.abhijithsreekar.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieReviews {
    @SerializedName("id")
    private long id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Reviews> reviews;

    @SerializedName("total_pages")
    private int pagesCount;

    @SerializedName("total_results")
    private int reviewsCount;

    public MovieReviews() {}

    public MovieReviews(long id, int page, ArrayList<Reviews> reviews, int pagesCount, int reviewsCount) {
        this.id = id;
        this.page = page;
        this.reviews = reviews;
        this.pagesCount = pagesCount;
        this.reviewsCount = reviewsCount;
    }

    public long getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }
}