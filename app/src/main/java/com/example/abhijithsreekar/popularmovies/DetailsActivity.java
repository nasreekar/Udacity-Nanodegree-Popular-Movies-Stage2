package com.example.abhijithsreekar.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhijithsreekar.popularmovies.Adapters.CustomTrailerAdapter;
import com.example.abhijithsreekar.popularmovies.Interface.MovieInterface;
import com.example.abhijithsreekar.popularmovies.Models.Movie;
import com.example.abhijithsreekar.popularmovies.Models.MovieTrailer;
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
        movieTitle.setText(selectedMovie.getTitle());
        movieLanguage.setText(new StringBuilder("Language: ").append(selectedMovie.getOriginalLanguage()));
        moviePlot.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(new StringBuilder("Release Date: ").append(selectedMovie.getReleaseDate()));
        movieVoteAverage.setText(new StringBuilder("Rating: ").append(selectedMovie.getVoteAverage()));

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(this.getResources().getString(R.string.IMAGE_BASE_URL) + selectedMovie.getBackdropPath())
                .placeholder((R.drawable.gradient_background))
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);
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
                        if (trailers != null) {
                            generateTrailerList(trailers);
                        }
                        else {
                            toggleNoTrailerMessage();
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

    private void generateTrailerList(final List<Trailer> trailers) {
        CustomTrailerAdapter adapter = new CustomTrailerAdapter(this, trailers);
        initTrailersAdapter(adapter);
    }

    private void initTrailersAdapter(CustomTrailerAdapter adapter) {
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(adapter);
    }

    private void toggleNoTrailerMessage() {

    }
}
