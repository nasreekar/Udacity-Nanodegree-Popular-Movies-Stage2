package com.example.abhijithsreekar.popularmovies.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.abhijithsreekar.popularmovies.Models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    LiveData<List<Movie>> loadAllFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovie(Movie movie);

    @Query("DELETE FROM movie WHERE movie_id = :movie_id")
    void deleteFavoriteMovie(Integer movie_id);
}
