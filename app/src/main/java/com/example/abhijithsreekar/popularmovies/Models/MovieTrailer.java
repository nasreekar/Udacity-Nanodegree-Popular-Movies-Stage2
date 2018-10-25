package com.example.abhijithsreekar.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieTrailer {

    @SerializedName("id")
    private long id;

    @SerializedName("results")
    private ArrayList<Trailer> results;

    public MovieTrailer() {
    }

    public MovieTrailer(long id, ArrayList<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
