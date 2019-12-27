package com.couponcafe.app.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.couponcafe.app.R;
import com.couponcafe.app.fragments.AllFragment;
import com.couponcafe.app.fragments.FoodDiningFragment;
import com.couponcafe.app.fragments.TravelFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BestOffersActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_offers);

        getSupportActionBar().setTitle("Best Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {



        viewPager = (ViewPager)findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("All"));
//        tabLayout.addTab(tabLayout.newTab().setText("Travel"));
//        tabLayout.addTab(tabLayout.newTab().setText("Food & Dining"));
//        tabLayout.addTab(tabLayout.newTab().setText("Mobiles & Tablets"));
//        tabLayout.addTab(tabLayout.newTab().setText("Computers,Laptops & Gaming"));
//        tabLayout.addTab(tabLayout.newTab().setText("Food & Dining"));
        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        //set viewpager adapter
//        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        pagerAdapter.addFragment(new AllFragment(),"");
//        pagerAdapter.addFragment(new TravelFragment(),"");
//        pagerAdapter.addFragment(new FoodDiningFragment(),"");
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(pagerAdapter);
//
//        //change Tab selection when swipe ViewPager
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                Toast.makeText(MainActivity.this, "Position: "+tab.getPosition(), Toast.LENGTH_SHORT).show();
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AllFragment(), "All");
        adapter.addFrag(new TravelFragment(), "Travel");
        adapter.addFrag(new FoodDiningFragment(), "Food & Dining");
        adapter.addFrag(new AllFragment(), "Mobiles & Tablets");
        adapter.addFrag(new TravelFragment(), "Computers,Laptops & Gaming");
//        adapter.addFrag(new SixFragment(), "SIX");
//        adapter.addFrag(new SevenFragment(), "SEVEN");
//        adapter.addFrag(new EightFragment(), "EIGHT");
//        adapter.addFrag(new NineFragment(), "NINE");
//        adapter.addFrag(new TenFragment(), "TEN");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    public class ViewPagerAdapter extends FragmentPagerAdapter {
//
//        private final List<Fragment> fragmentList = new ArrayList<>();
//        private final List<String> FragmentListTitle = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if (position == 0) {
//                return new AllFragment();
//            }
//            else if (position == 1) {
//                return new TravelFragment();
//            }
//            else  return  new FoodDiningFragment();
//
//        }
//
//        @Override
//        public int getCount() {
//            return FragmentListTitle.size();
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return FragmentListTitle.get(position);
//        }
//
//        public void addFragment(Fragment fragment, String title)
//        {
//            fragmentList.add(fragment);
//            FragmentListTitle.add(title);
//
//        }
//    }


}
