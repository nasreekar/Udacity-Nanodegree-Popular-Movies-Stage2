package com.example.abhijithsreekar.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhijithsreekar.popularmovies.Models.Reviews;
import com.example.abhijithsreekar.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomReviewsAdapter extends RecyclerView.Adapter<CustomReviewsAdapter.MovieReviewViewHolder> {

    private Context context;
    private List<Reviews> reviewList;

    public CustomReviewsAdapter(Context context, List<Reviews> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_reviews_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieReviewViewHolder holder, int position) {
        Reviews review = reviewList.get(position);

        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList == null ? 0 : reviewList.size();
    }

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_content)
        TextView content;

        MovieReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
