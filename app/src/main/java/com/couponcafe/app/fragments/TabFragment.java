package com.couponcafe.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.activities.CategoriesDetailsActivity;
import com.couponcafe.app.adapter.TodayBestOfferListAdapter;
import com.couponcafe.app.models.BestOfferDatum;

import java.util.ArrayList;

public class TabFragment extends Fragment implements CategoriesDetailsActivity.OnAboutDataReceivedListener {

    int position;
    private TextView textView;
    RecyclerView recylerview;
    TodayBestOfferListAdapter todayBestOfferListAdapter;
    ArrayList<BestOfferDatum>bestOfferData;

    CategoriesDetailsActivity mActivity;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        recylerview = view.findViewById(R.id.recylerview);
        mActivity = (CategoriesDetailsActivity) getActivity();
        mActivity.setAboutDataListener(this);

       // Log.e("testing", "onCreateView:size "+bestOfferData.size());
        todayBestOfferListAdapter = new TodayBestOfferListAdapter(bestOfferData,getActivity());
       // recylerview.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recylerview.setLayoutManager(mLayoutManager);
        recylerview.setAdapter(todayBestOfferListAdapter);
        recylerview.setNestedScrollingEnabled(false);
        todayBestOfferListAdapter.notifyDataSetChanged();
        return view ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.textView);

        textView.setText("Fragment " + (position + 1));

    }

    @Override
    public void onDataReceived(ArrayList<BestOfferDatum> bestOfferData) {
        this.bestOfferData = bestOfferData;
    }
}