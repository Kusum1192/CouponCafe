package com.couponcafe.app.adapter;

import com.couponcafe.app.interfaces.ParentListItem;

import java.util.List;

public class MovieCategory implements ParentListItem {
    private String mName,subname;
    private List<Movies> mMovies;

    public MovieCategory(String name, String subcatname, List<Movies> movies) {
        mName = name;
        subname = subcatname;
        mMovies = movies;
    }

    public String getName() {
        return mName;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    @Override
    public List<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}