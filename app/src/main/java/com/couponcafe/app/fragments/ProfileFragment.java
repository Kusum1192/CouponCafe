package com.couponcafe.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.couponcafe.app.R;
import com.couponcafe.app.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;




public class ProfileFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    //Context context;
    View view;

    public ProfileFragment() {
        // Required empty public constructor
       // Toast.makeText(getActivity(), "profile call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        initView(view);
        return view;
    }

    public void initView(View view){
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager_child);
        //if (viewPager != null) {
           // setupViewPager(viewPager);
       // }
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

    }

//    private void setupViewPager(ViewPager viewPager) {
//       // FragmentManager cfManager = getChildFragmentManager();
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
//        adapter.addFrag(new OverviewFragment(), "Overview");
//        adapter.addFrag(new ActivitiesFragment(), "Activites");
//        adapter.addFrag(new WithdrawalsFragment(), "Withdrawals");
//        //viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(adapter);
//
//    }




}
