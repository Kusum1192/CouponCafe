package com.couponcafe.app.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.R;
import com.couponcafe.app.fragments.AllFragment;
import com.couponcafe.app.fragments.FoodDiningFragment;
import com.couponcafe.app.fragments.TabFragment;
import com.couponcafe.app.fragments.TravelFragment;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.AllCategoriesDetailsModel;
import com.couponcafe.app.models.BestOfferDatum;
import com.couponcafe.app.models.SubCategory;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    ProgressDialog progressDialog;
    private OnAboutDataReceivedListener mAboutDataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_offers);

        getSupportActionBar().setTitle("Best Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getCategoriesDetails();

        //  init();
//        Intent intent = getIntent();
//        if(intent!=null){
//            String catId = String.valueOf(intent.getIntExtra("subcatId",0));
//            String subcatName = intent.getStringExtra("subcatName");
//
//        }
    }

    private void getCategoriesDetails() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<AllCategoriesDetailsModel> call = apiService.allcategoriesDetails(Constants.getSharedPreferenceInt(CategoriesDetailsActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(CategoriesDetailsActivity.this, "securitytoken", ""),
                Constants.getSharedPreferenceString(CategoriesDetailsActivity.this, "versionName", ""),
                Constants.getSharedPreferenceInt(CategoriesDetailsActivity.this, "versionCode", 0));

        if (!((Activity) CategoriesDetailsActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(CategoriesDetailsActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<AllCategoriesDetailsModel>() {
            @Override
            public void onResponse(Call<AllCategoriesDetailsModel> call, Response<AllCategoriesDetailsModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            ArrayList<SubCategory> allCategoriesDetailsModels = response.body().getSubCategories();
                            ArrayList<BestOfferDatum> bestOfferData = response.body().getBestOfferData();
                            viewPager = (ViewPager) findViewById(R.id.view_pager);
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), allCategoriesDetailsModels, bestOfferData);
                            viewPager.setAdapter(adapter);

                            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                            tabLayout.setupWithViewPager(viewPager);

                        } else {
                            Toast.makeText(CategoriesDetailsActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(CategoriesDetailsActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<AllCategoriesDetailsModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });


    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        dismissProgressDialog();
        super.onPause();
    }

//    class ViewPagerAdapter extends FragmentPagerAdapter {
//
//       // private String title[] = {"One", "Two", "Three"};
//        ArrayList<SubCategory> allCategoriesDetailsModels;
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        public ViewPagerAdapter(FragmentManager supportFragmentManager, ArrayList<SubCategory> allCategoriesDetailsModels) {
//            super(supportFragmentManager);
//            this.allCategoriesDetailsModels = allCategoriesDetailsModels;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return TabFragment.getInstance(position);
//        }
//
//        @Override
//        public int getCount() {
//            return allCategoriesDetailsModels.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return allCategoriesDetailsModels.get(position);
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    private void init() {
//        viewPager = (ViewPager)findViewById(R.id.view_pager);
//        setupViewPager(viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
//    }


//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFrag(new AllFragment(), "All");
//        adapter.addFrag(new TravelFragment(), "Travel");
//        adapter.addFrag(new FoodDiningFragment(), "Food & Dining");
//        adapter.addFrag(new AllFragment(), "Mobiles & Tablets");
//        adapter.addFrag(new TravelFragment(), "Computers,Laptops & Gaming");
//        viewPager.setAdapter(adapter);
//    }

    @Override
    public void onClick(View view) {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        //        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
        ArrayList<SubCategory> allCategoriesDetailsModels;
        ArrayList<BestOfferDatum> bestOfferData;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public ViewPagerAdapter(FragmentManager manager, ArrayList<SubCategory> allCategoriesDetailsModels) {
            super(manager);
            this.allCategoriesDetailsModels = allCategoriesDetailsModels;
        }

        public ViewPagerAdapter(FragmentManager supportFragmentManager, ArrayList<SubCategory> allCategoriesDetailsModels,
                                ArrayList<BestOfferDatum> bestOfferData) {
            super(supportFragmentManager);
            this.allCategoriesDetailsModels = allCategoriesDetailsModels;
            this.bestOfferData = bestOfferData;
        }

        @Override
        public Fragment getItem(int position) {
            return TabFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return allCategoriesDetailsModels.size();
        }

//        public void addFrag(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }

        @Override
        public CharSequence getPageTitle(int position) {
            return allCategoriesDetailsModels.get(position).getSubCategoryName();
        }
    }

    public interface OnAboutDataReceivedListener {
        void onDataReceived(ArrayList<BestOfferDatum> bestOfferData);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }
}
