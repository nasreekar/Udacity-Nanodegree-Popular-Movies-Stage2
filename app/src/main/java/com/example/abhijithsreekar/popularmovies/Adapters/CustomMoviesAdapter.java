package com.example.abhijithsreekar.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhijithsreekar.popularmovies.DetailsActivity;
import com.example.abhijithsreekar.popularmovies.Models.Movie;
import com.example.abhijithsreekar.popularmovies.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.abhijithsreekar.popularmovies.Utils.Constants.SELECTED_MOVIE_TO_SEE_DETAILS;

public class CustomMoviesAdapter extends RecyclerView.Adapter<CustomMoviesAdapter.CustomMovieViewHolder> {

    private List<Movie> dataList;
    private Context context;

    public CustomMoviesAdapter(Context context, List<Movie> dataList, RecyclerView recyclerView) {
        this.context = context;
        this.dataList = dataList;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    }

    @NonNull
    @Override
    public CustomMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_movie_item, parent, false);
        return new CustomMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomMovieViewHolder holder, int position) {
        holder.bindMovie(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(List<Movie> movies) {
        this.dataList = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Movie> movies) {
        this.dataList.addAll(movies);
        notifyDataSetChanged();
    }

    class CustomMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context mContext;

        @BindView(R.id.tvMovieTitle)
        TextView movieTitle;
        @BindView(R.id.ivMovieImage)
        ImageView coverImage;
        @BindView(R.id.tvReleaseDate)
        TextView releaseDate;

        CustomMovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        void bindMovie(Movie movie) {
            StringBuilder releaseText = new StringBuilder().append(mContext.getResources().getString(R.string.Release_Date_Title));
            releaseText.append(movie.getReleaseDate());

            movieTitle.setText(movie.getOriginalTitle());
            releaseDate.setText(releaseText);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(context.getResources().getString(R.string.IMAGE_BASE_URL) + movie.getPosterPath())
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage);
        }

        @Override
        public void onClick(View v) {
            Movie movie = dataList.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(SELECTED_MOVIE_TO_SEE_DETAILS, movie);
            v.getContext().startActivity(intent);
        }
    }
}

