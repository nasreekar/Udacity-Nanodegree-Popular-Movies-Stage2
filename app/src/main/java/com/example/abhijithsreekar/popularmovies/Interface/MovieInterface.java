package com.example.abhijithsreekar.popularmovies.Interface;

import com.example.abhijithsreekar.popularmovies.Models.Movie;
import com.example.abhijithsreekar.popularmovies.Models.MovieCasting;
import com.example.abhijithsreekar.popularmovies.Models.MovieResponse;
import com.example.abhijithsreekar.popularmovies.Models.MovieReviews;
import com.example.abhijithsreekar.popularmovies.Models.MovieTrailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                          @Query("page") int page);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                         @Query("page") int page);

    @GET("movie/{id}?api_key=")
    Call<Movie> getMovie(@Path("id") long id);

    @GET("movie/{id}/videos?api_key=")
    Call<MovieTrailer> getMovieTrailers(@Path("id") long id);

    @GET("movie/{id}/reviews?api_key=")
    Call<MovieReviews> getMovieReviews(@Path("id") long id);

    @GET("movie/{id}/credits?api_key=")
    Call<MovieCasting> getMovieCasting(@Path("id") long id);
}

