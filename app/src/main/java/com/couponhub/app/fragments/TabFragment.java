package com.couponhub.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;

import com.couponhub.app.activities.OffersDetailsActivity;
import com.couponhub.app.adapter.RecyclerTouchListener;
import com.couponhub.app.adapter.TodayBestOfferListAdapter;
import com.couponhub.app.models.BestOfferDatum;

import java.util.ArrayList;

public class TabFragment extends Fragment  {

    int position;
    TextView textView;
    RecyclerView recylerview;
    TodayBestOfferListAdapter todayBestOfferListAdapter;
    ArrayList<BestOfferDatum>bestOfferData;
    String TAG = "testing_tab";

    protected  FragmentActivity mActivity;

    public TabFragment() {

    }

    public TabFragment(ArrayList<BestOfferDatum> bestOfferData) {
        this.bestOfferData = bestOfferData;
    }


    public static Fragment getInstance(int position, ArrayList<BestOfferDatum> bestOfferData) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment(bestOfferData);
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

        try{
        recylerview = view.findViewById(R.id.recylerview);

       // Log.e("testing", "onCreateView:size "+bestOfferData.size());
        todayBestOfferListAdapter = new TodayBestOfferListAdapter(bestOfferData,mActivity);
       // recylerview.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recylerview.setLayoutManager(mLayoutManager);
        recylerview.setAdapter(todayBestOfferListAdapter);
        recylerview.setNestedScrollingEnabled(false);
        recylerview.addOnItemTouchListener(new RecyclerTouchListener(mActivity, recylerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                BestOfferDatum bestOfferDatum = bestOfferData.get(position);
                // Toast.makeText(mActivity, topStores.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mActivity, OffersDetailsActivity.class);
                intent.putExtra("offerId",bestOfferDatum.getOfferId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        }catch (Exception ex){
        ex.printStackTrace();
            Log.e(TAG, "onCreateView: "+ex );
        }
        return view ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;
        }

    }

}