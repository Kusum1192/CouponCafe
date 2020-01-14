package com.couponcafe.app.adapter;

import java.util.ArrayList;

public class Movies extends ArrayList<Movies> {

    private String mName;

    public Movies(String name) {
        mName = name;

    }

    public String getName() {
        return mName;
    }
}