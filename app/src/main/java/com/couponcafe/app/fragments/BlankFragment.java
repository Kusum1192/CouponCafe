package com.couponcafe.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class BlankFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.view_pager_child);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setViewPager(ViewPager viewPager){

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new AllFragment(),"Overview");
        pagerAdapter.addFragment(new ActivitiesFragment(),"Activities");
//        pagerAdapter.addFragment(new AllFragment(),"Withdrawals");
       // viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);


    }

}
