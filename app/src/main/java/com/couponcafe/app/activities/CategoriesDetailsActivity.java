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
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.R;
import com.couponcafe.app.fragments.TabFragment;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.AllCategoriesDetailsModel;
import com.couponcafe.app.models.BestOfferDatum;
import com.couponcafe.app.models.SubCategory;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    ProgressDialog progressDialog;
    String TAG = "testing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_offers);

        getSupportActionBar().setTitle("Best Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //  init();
        Intent intent = getIntent();
        if(intent!=null){
            String subcatId = String.valueOf(intent.getIntExtra("subcatId",0));
            String catId = String.valueOf(intent.getIntExtra("catId",0));
            String sno = String.valueOf(intent.getIntExtra("sno",0));
            String subcatName = intent.getStringExtra("subcatName");
            getCategoriesDetails(catId,subcatId,sno);
            Log.e(TAG, "onCreate:catid1111 "+catId +"\n subcat: "+subcatId +"\n sno: "+sno);
        }
    }

    private void getCategoriesDetails(final String catId,final String subcatId,final String sno) {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<AllCategoriesDetailsModel> call = apiService.allcategoriesDetails(catId,subcatId,Constants.getSharedPreferenceInt(CategoriesDetailsActivity.this, "userId", 0),
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

                            //Toast.makeText(CategoriesDetailsActivity.this, "click child:cat "+catId, Toast.LENGTH_SHORT).show();
                            int sno_pos = Integer.parseInt(sno);
                            viewPager.setCurrentItem(sno_pos);

                            Log.e(TAG, "onResponse: "+sno_pos);

                            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                            tabLayout.setupWithViewPager(viewPager);
//                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//                                @Override
//                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                                }
//
//                                @Override
//                                public void onPageSelected(int position) {
//                                    // Here's your instance
//                                    YourFragment fragment =(YourFragment)yourPagerAdapter.getRegisteredFragment(position);
//
//                                }
//
//                                @Override
//                                public void onPageScrollStateChanged(int state) {
//
//                                }
//                            });

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



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View view) {

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

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
            return TabFragment.getInstance(position,bestOfferData);
        }

        @Override
        public int getCount() {
            return allCategoriesDetailsModels.size();
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return allCategoriesDetailsModels.get(position).getSubCategoryName();
        }



    }


}
