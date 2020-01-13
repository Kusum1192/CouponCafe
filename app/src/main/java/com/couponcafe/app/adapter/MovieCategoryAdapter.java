package com.couponcafe.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.couponcafe.app.R;
import com.couponcafe.app.interfaces.ParentListItem;
import com.couponcafe.app.models.CategoryDatum;
import com.couponcafe.app.viewholders.MovieCategoryViewHolder;
import com.couponcafe.app.viewholders.MoviesViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryAdapter extends ExpandableRecyclerAdapter<MovieCategoryViewHolder, MoviesViewHolder> {

    private LayoutInflater mInflator;
    Context mcontext;

    public MovieCategoryAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.mcontext = context;
        mInflator = LayoutInflater.from(context);
    }




    @Override
    public MovieCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.movie_category_view, parentViewGroup, false);
        return new MovieCategoryViewHolder(mcontext,movieCategoryView);
    }

    @Override
    public MoviesViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.movies_view, childViewGroup, false);
        return new MoviesViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(MovieCategoryViewHolder movieCategoryViewHolder, int position, ParentListItem parentListItem) {
        MovieCategory movieCategory = (MovieCategory) parentListItem;
        movieCategoryViewHolder.bind(movieCategory);

    }

    @Override
    public void onBindChildViewHolder(MoviesViewHolder moviesViewHolder, int position, Object childListItem) {
        Movies movies = (Movies) childListItem;
        moviesViewHolder.bind(movies);


    }
}