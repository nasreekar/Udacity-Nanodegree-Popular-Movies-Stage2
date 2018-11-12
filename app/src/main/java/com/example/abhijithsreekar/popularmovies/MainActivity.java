package com.example.abhijithsreekar.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhijithsreekar.popularmovies.Adapters.CustomMoviesAdapter;
import com.example.abhijithsreekar.popularmovies.Interface.MovieInterface;
import com.example.abhijithsreekar.popularmovies.Models.Movie;
import com.example.abhijithsreekar.popularmovies.Models.MovieResponse;
import com.example.abhijithsreekar.popularmovies.Network.APIClient;
import com.example.abhijithsreekar.popularmovies.Utils.MovieUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abhijithsreekar.popularmovies.Utils.Constants.ACTIVITY_TITLE;
import static com.example.abhijithsreekar.popularmovies.Utils.Constants.FAV_MOVIE_KEY;
import static com.example.abhijithsreekar.popularmovies.Utils.Constants.MOVIES_LIST;
import static com.example.abhijithsreekar.popularmovies.Utils.Constants.RECYCLER_VIEW_LAYOUT_MANAGER_STATE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.rv_main)
    public RecyclerView rvMain;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    private static String API_KEY;
    public ArrayList<Movie> movies;
    private int currentPage = 1;
    MainActivityViewModel viewModel;
    CustomMoviesAdapter adapter;
    private MovieInterface movieService;
    private static String actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API_KEY = getResources().getString(R.string.API_KEY);

        ButterKnife.bind(this);
        movies = new ArrayList<>();

        adapter = new CustomMoviesAdapter(this, movies, rvMain);
        rvMain.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        /*viewModel.getFavoriteMovies().observe(this, favorites -> {
            if (favorites != null) {
                if (movies == null) {
                    movies = new ArrayList<>();
                } else {
                    adapter.clear();
                }
                adapter.addAll(favorites);
            }
        });*/

        movieService = APIClient.getRetrofitInstance().create(MovieInterface.class);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIES_LIST)) {
                progressBar.setVisibility(View.GONE);
                setTitle(savedInstanceState.getString(ACTIVITY_TITLE));
                movies = savedInstanceState.getParcelableArrayList(MOVIES_LIST);
                adapter.clear();
                adapter.setData(movies);
            }
        }

        if (savedInstanceState == null) {
            progressBar.setVisibility(View.VISIBLE);
            getPopularMovies();
        }
    }

    private void getPopularMovies() {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {

            Call<MovieResponse> call = movieService.getPopularMovies(API_KEY, getResources().getString(R.string.LANGUAGE), currentPage);
            Log.i("Popular movies api", movieService.getPopularMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1).request().url().toString());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response.body().getMovies() != null) {
                            Log.d(TAG, "Number of popular movies received: " + response.body().getMovies().size());
                            adapter.addAll(response.body().getMovies());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void getTopRatedMovies() {
        if (MovieUtils.getInstance().isNetworkAvailable(this)) {

            Call<MovieResponse> call = movieService.getTopRatedMovies(API_KEY, getResources().getString(R.string.LANGUAGE), currentPage);
            Log.i("Top movies api", movieService.getTopRatedMovies(API_KEY, getResources().getString(R.string.LANGUAGE), 1).request().url().toString());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response.body().getMovies() != null) {
                            Log.d(TAG, "Number of top rated movies received: " + response.body().getMovies().size());
                            adapter.addAll(response.body().getMovies());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i("Network Connection Status", "Not available");
        }
    }

    private void getFavoriteMovies() {
        viewModel.getFavoriteMovies().observe(this, favoriteMovies -> {
            if (favoriteMovies != null && favoriteMovies.size() > 0) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(TAG, "updating list of movies from Live data in ViewModel");
                adapter.addAll(favoriteMovies);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "No favorite Movies Found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.topRated:
                progressBar.setVisibility(View.VISIBLE);
                adapter.clear();
                getTopRatedMovies();
                setTitle(R.string.toprated_movies);
                actionBarTitle = getResources().getString(R.string.toprated_movies);
                break;
            case R.id.popular:
                progressBar.setVisibility(View.VISIBLE);
                adapter.clear();
                getPopularMovies();
                setTitle(R.string.popular_movies);
                actionBarTitle = getResources().getString(R.string.popular_movies);
                break;

            case R.id.favorite:
                progressBar.setVisibility(View.VISIBLE);
                adapter.clear();
                getFavoriteMovies();
                setTitle(R.string.favorite_movies);
                actionBarTitle = getResources().getString(R.string.favorite_movies);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES_LIST, movies);
        outState.putString(ACTIVITY_TITLE, actionBarTitle);
        outState.putParcelable(RECYCLER_VIEW_LAYOUT_MANAGER_STATE, rvMain.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean(FAV_MOVIE_KEY)) {
                adapter.clear();
                setTitle(R.string.favorite_movies);
                getFavoriteMovies();
            }
        }
    }

}
