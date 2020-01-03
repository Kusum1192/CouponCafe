package com.couponcafe.app.adapter;

public class Movies {

    private String mName,mMovieSubCatOffers;

    public Movies(String name,String mMovieSubCatOffers) {
        mName = name;
        this.mMovieSubCatOffers = mMovieSubCatOffers;
    }

    public String getmMovieSubCatOffers() {
        return mMovieSubCatOffers;
    }

    public void setmMovieSubCatOffers(String mMovieSubCatOffers) {
        this.mMovieSubCatOffers = mMovieSubCatOffers;
    }

    public String getName() {
        return mName;
    }
}