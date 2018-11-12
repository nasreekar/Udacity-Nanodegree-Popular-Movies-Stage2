package com.example.abhijithsreekar.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.abhijithsreekar.popularmovies.Database.MovieRepository;
import com.example.abhijithsreekar.popularmovies.Models.Movie;

public class DetailsActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public DetailsActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void addMovieToFavorites(Movie movie) {
        movieRepository.addMovieToFavorites(movie);
    }

    public void removeMovieFromFavorites(int id) {
        movieRepository.deleteFavoriteMovie(id);
    }
}
