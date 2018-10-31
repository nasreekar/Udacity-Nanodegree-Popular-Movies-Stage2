package com.example.abhijithsreekar.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhijithsreekar.popularmovies.Adapters.CustomCastAdapter;
import com.example.abhijithsreekar.popularmovies.Adapters.CustomReviewsAdapter;
import com.example.abhijithsreekar.popularmovies.Adapters.CustomTrailerAdapter;
import com.example.abhijithsreekar.popularmovies.Interface.MovieInterface;
import com.example.abhijithsreekar.popularmovies.Models.Cast;
import com.example.abhijithsreekar.popularmovies.Models.Movie;
import com.example.abhijithsreekar.popularmovies.Models.MovieCredits;
import com.example.abhijithsreekar.popularmovies.Models.MovieReviews;
import com.example.abhijithsreekar.popularmovies.Models.MovieTrailer;
import com.example.abhijithsreekar.popularmovies.Models.Reviews;
import com.example.abhijithsreekar.popularmovies.Models.Trailer;
import com.example.abhijithsreekar.popularmovies.Network.APIClient;
import com.example.abhijithsreekar.popularmovies.Utils.MovieUtils;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    private static String API_KEY;
    public List<Trailer> trailers;
    public List<Reviews> reviews;
    public List<Cast> cast;

    @BindView(R.id.iv_details_moviePoster)
    ImageView moviePoster;

    @BindView(R.id.tv_details_MovieTitle)
    TextView movieTitle;

    @BindView(R.id.tv_details_Language)
    TextView movieLanguage;

    @BindView(R.id.tv_details_plot)
    TextView moviePlot;

    @BindView(R.id.tv_details_releaseDate)
    TextView movieReleaseDate;

    @BindView(R.id.tv_details_voteAverage)
    TextView movieVoteAverage;

    @BindView(R.id.rv_trailer)
    public RecyclerView rvTrailer;

    @BindView(R.id.rv_reviews)
    public RecyclerView rvReviews;

    @BindView(R.id.rv_cast)
    public RecyclerView rvCast;

    @BindView(R.id.tv_cast_not_available)
    TextView castNotAvailable;

    @BindView(R.id.tv_trailers_not_available)
    TextView trailersNotAvailable;

    @BindView(R.id.tv_reviews_not_available)
    TextView reviewsNotAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();
        API_KEY = getResources().getString(R.string.API_KEY);

        ButterKnife.bind(this);
        bindSelectedMovieData();
    }

    private void bindSelectedMovieData() {
        Intent intent = getIntent();
        Movie selectedMovie = intent.getParcelableExtra("Movie");

        getMovieTrailers(selectedMovie.getId());
        getMovieReviews(selectedMovie.getId());
        getMovieCast(selectedMovie.getId());

        movieTitle.setText(selectedMovie.getTitle());
        movieLanguage.setText(new StringBuilder("Language: ").append(selectedMovie.getOriginalLanguage()));
        if (selectedMovie.getOverview() != null && !selectedMovie.getOverview().isEmpty()) {
            moviePlot.setText(selectedMovie.getOverview());
        } else {
            moviePlot.setText(getResources().getString(R.string.plotNotAvailable));
        }
        movieReleaseDate.setText(new StringBuilder("Release Date: ").append(selectedMovie.getReleaseDate()));
        movieVoteAverage.setText(new StringBuilder("Rating: ").append(selectedMovie.getVoteAverage()));

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(this.getResources().getString(R.string.IMAGE_BASE_URL) + selectedMovie.getBackdropPath())
                .placeholder((R.drawable.gradient_background))
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);
    }

    private void getMovieCast(Integer id) {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = APIClient.getRetrofitInstance();
            }
            MovieInterface movieService = retrofit.create(MovieInterface.class);
            Call<MovieCredits> call = movieService.getMovieCredits(id, API_KEY);
            call.enqueue(new Callback<MovieCredits>() {
                @Override
                public void onResponse(@NonNull Call<MovieCredits> call, @NonNull Response<MovieCredits> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        cast = response.body().getCast();
                        if (cast != null && !cast.isEmpty()) {
                            rvCast.setVisibility(View.VISIBLE);
                            castNotAvailable.setVisibility(View.GONE);
                            generateCreditsList(cast);
                        } else {
                            rvCast.setVisibility(View.GONE);
                            castNotAvailable.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieCredits> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void getMovieReviews(Integer id) {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = APIClient.getRetrofitInstance();
            }
            MovieInterface movieService = retrofit.create(MovieInterface.class);
            Call<MovieReviews> call = movieService.getMovieReviews(id, API_KEY, 1);
            call.enqueue(new Callback<MovieReviews>() {
                @Override
                public void onResponse(@NonNull Call<MovieReviews> call, @NonNull Response<MovieReviews> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        reviews = response.body().getReviewList();
                        if (reviews != null && !reviews.isEmpty()) {
                            rvReviews.setVisibility(View.VISIBLE);
                            reviewsNotAvailable.setVisibility(View.GONE);
                            generateReviewList(reviews);
                        } else {
                            rvReviews.setVisibility(View.GONE);
                            reviewsNotAvailable.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieReviews> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void getMovieTrailers(Integer id) {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {
            if (retrofit == null) {
                retrofit = APIClient.getRetrofitInstance();
            }
            MovieInterface movieService = retrofit.create(MovieInterface.class);
            Call<MovieTrailer> call = movieService.getMovieTrailers(id, API_KEY);
            call.enqueue(new Callback<MovieTrailer>() {
                @Override
                public void onResponse(@NonNull Call<MovieTrailer> call, @NonNull Response<MovieTrailer> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        trailers = response.body().getTrailers();
                        if (trailers != null && !trailers.isEmpty()) {
                            rvTrailer.setVisibility(View.VISIBLE);
                            trailersNotAvailable.setVisibility(View.GONE);
                            generateTrailerList(trailers);
                        } else {
                            rvTrailer.setVisibility(View.GONE);
                            trailersNotAvailable.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieTrailer> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void generateCreditsList(List<Cast> cast) {
        CustomCastAdapter adapter = new CustomCastAdapter(this, cast);
        initCastAdapter(adapter);
    }

    private void generateReviewList(final List<Reviews> reviews) {
        CustomReviewsAdapter adapter = new CustomReviewsAdapter(this, reviews);
        initReviewsAdapter(adapter);
    }

    private void generateTrailerList(final List<Trailer> trailers) {
        CustomTrailerAdapter adapter = new CustomTrailerAdapter(this, trailers);
        initTrailersAdapter(adapter);
    }

    private void initCastAdapter(CustomCastAdapter adapter) {
        rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCast.setAdapter(adapter);
    }

    private void initReviewsAdapter(CustomReviewsAdapter adapter) {
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        rvReviews.setAdapter(adapter);
    }

    private void initTrailersAdapter(CustomTrailerAdapter adapter) {
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(adapter);
    }
}
