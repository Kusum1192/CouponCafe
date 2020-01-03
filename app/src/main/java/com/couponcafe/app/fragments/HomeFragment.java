package com.couponcafe.app.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.couponcafe.app.R;
import com.couponcafe.app.activities.BestOffersActivity;
import com.couponcafe.app.utils.Sliding_Adapter_For_viewpager_main;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] images = {R.drawable.one, R.drawable.oyoroom, R.drawable.time_prime};
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    private CircleIndicator indicator;
    CardView cardview_bestoffer;


    public HomeFragment() {
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        mPager = (ViewPager) root.findViewById(R.id.pager);
        cardview_bestoffer = root.findViewById(R.id.cardview_bestoffer);
        indicator = (CircleIndicator) root.findViewById(R.id.indicator);

        cardview_bestoffer.setOnClickListener(this);
        init();
        return root;
    }

    private void init() {
        for (int i = 0; i < images.length; i++)
            ImageArray.add(images[i]);


        mPager.setAdapter(new Sliding_Adapter_For_viewpager_main(getActivity(), ImageArray));

        indicator.setViewPager(mPager);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_bestoffer:
                Intent intent_bestOffers_activity = new Intent(getActivity(), BestOffersActivity.class);
                startActivity(intent_bestOffers_activity);
                break;

        }
    }
}