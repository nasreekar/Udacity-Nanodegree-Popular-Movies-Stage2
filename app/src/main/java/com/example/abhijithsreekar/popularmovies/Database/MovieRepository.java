package com.example.abhijithsreekar.popularmovies.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.abhijithsreekar.popularmovies.Models.Movie;
import com.example.abhijithsreekar.popularmovies.Utils.AppExecutors;

import java.util.List;

public class MovieRepository {
    private MovieDao movieDao;
    private AppExecutors appExecutors;

    private LiveData<List<Movie>> movies;

    public MovieRepository(Application application) {
        movieDao = MovieDatabase.getInstance(application).movieDao();
        movies = movieDao.loadAllFavoriteMovies();

        appExecutors = AppExecutors.getExecutorInstance();
    }

    public LiveData<List<Movie>> loadAllFavoriteMovies() {
        return movies;
    }

    public void updateFavoriteMovie(Movie movie) {
        appExecutors.getDiskIO().execute(() -> movieDao.updateFavoriteMovie(movie));
    }

    public void addMovieToFavorites(Movie movie) {
        appExecutors.getDiskIO().execute(() -> movieDao.insertFavoriteMovie(movie));
    }

    public void deleteFavoriteMovie(int id) {
        appExecutors.getDiskIO().execute(() -> movieDao.deleteFavoriteMovie(id));
    }
}
