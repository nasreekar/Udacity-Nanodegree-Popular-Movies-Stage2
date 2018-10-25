package com.example.abhijithsreekar.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieCasting {
    @SerializedName("id")
    private long id;

    @SerializedName("cast")
    private ArrayList<Cast> cast;

    public MovieCasting() {}

    public MovieCasting(long id, ArrayList<Cast> cast) {
        this.id = id;
        this.cast = cast;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}