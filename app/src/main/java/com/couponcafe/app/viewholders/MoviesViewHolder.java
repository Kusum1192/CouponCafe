package com.couponcafe.app.viewholders;

import android.view.View;
import android.widget.TextView;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.Movies;

public class MoviesViewHolder extends ChildViewHolder {

    private TextView mMoviesTextView,mMovieSubCatOffers;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);
       // mMovieSubCatOffers = (TextView) itemView.findViewById(R.id.tv_subcat_offers);
    }

    public void bind(Movies movies) {
        mMoviesTextView.setText(movies.getName());
        //mMovieSubCatOffers.setText(movies.getmMovieSubCatOffers());
    }
}