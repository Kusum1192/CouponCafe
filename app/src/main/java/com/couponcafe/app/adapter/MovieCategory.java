package com.couponcafe.app.adapter;

import androidx.annotation.NonNull;

import com.couponcafe.app.interfaces.ParentListItem;
import com.couponcafe.app.models.CategoryDatum;
import com.couponcafe.app.models.SubCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MovieCategory extends ArrayList<MovieCategory> implements ParentListItem {
    private String mName;
    private ArrayList<Movies> mMovies;
    //Movies subCategoryName;
    public MovieCategory(String name, ArrayList<Movies> subCategories) {
        mName = name;
        mMovies = subCategories;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


    public String getName() {
        return mName;
    }


    @Override
    public ArrayList<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


}