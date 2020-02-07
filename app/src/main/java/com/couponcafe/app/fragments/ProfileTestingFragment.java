package com.couponcafe.app.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.R;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.BestOfferDatum;
import com.couponcafe.app.models.ProfileDataModel;
import com.couponcafe.app.models.SubCategory;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.CircleTransform;
import com.couponcafe.app.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileTestingFragment extends Fragment {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    ProgressDialog progressDialog;
    ImageView imageView_profile;
    TextView tv_useremail,tv_username,tv_total;
    protected FragmentActivity mActivity;
    ViewPager viewPager;
    View view;


    public ProfileTestingFragment() {
        // Required empty public constructor
       // Toast.makeText(mActivity, "profile call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.shoping_fragment, container, false);

        initView(view);

        getProfileData();
        return view;
    }

    public void initView(View view){
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)view.findViewById(R.id.frameLayout);
        imageView_profile=view.findViewById(R.id.imageView_profile);
        tv_username = view.findViewById(R.id.tv_username);
        tv_useremail = view.findViewById(R.id.tv_useremail);
        tv_total = view.findViewById(R.id.tv_total);


    }

    private void getProfileData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ProfileDataModel> call = apiService.getprofileData(Constants.getSharedPreferenceInt(mActivity,"userId",0),
                Constants.getSharedPreferenceString(mActivity,"securitytoken",""),
                Constants.getSharedPreferenceString(mActivity,"versionName",""),
                Constants.getSharedPreferenceInt(mActivity,"versionCode",0));

        if(!((Activity) mActivity).isFinishing()) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<ProfileDataModel>() {
            @Override
            public void onResponse(Call<ProfileDataModel>call, final Response<ProfileDataModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){
//                            Picasso.get().load(response.body().getSocialImgurl())
//                                    .placeholder(R.drawable.ic_placeholder_small)
//                                    .error(R.drawable.ic_placeholder_small).transform(new CircleTransform())
//                                    .into((imageView_profile));
//                            tv_useremail.setText(response.body().getSocialEmail());
//                            tv_username.setText(response.body().getSocialName());
//                            tv_total.setText(Constants.getSharedPreferenceString(mActivity,"currency","")+""+response.body().getUserSavings());

                            viewPager = (ViewPager)view.findViewById(R.id.view_pager);
                            FragmentManager cfManager = getChildFragmentManager();
                            ViewPagerAdapter adapter = new ViewPagerAdapter(cfManager, response);
                            viewPager.setAdapter(adapter);
//                            viewPager.setSaveFromParentEnabled(false);

                            tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
                            tabLayout.setupWithViewPager(viewPager);





                        }else{
                            Toast.makeText(mActivity,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(mActivity,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ProfileDataModel>call, Throwable t) {
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;

        }

    }



    public class ViewPagerAdapter extends FragmentStatePagerAdapter  {

        Response<ProfileDataModel> response;

        public ViewPagerAdapter(FragmentManager fm,Response response) {
            super(fm);
            this.response = response;
        }

        public ViewPagerAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        public ViewPagerAdapter(FragmentManager supportFragmentManager, Response<ProfileDataModel> response, Resources resources) {
            super(supportFragmentManager);
            this.response = response;
        }


        @Override
        public Fragment getItem(int position) {
//            Fragment fragment = null;
            switch (position) {
                case 0:
                    return new OverviewFragment(response.body().getUserAmount(), response.body().getPendingAmount(), response.body().getProducts());
//                    return new OverviewFragment();

                case 1:
                    return new WithdrawalsFragment(response.body().getUserAmount(), response.body().getPendingAmount(),response.body().getMinPayLimit());
//                    return new WithdrawalsFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
            {
                title = "OverView";
            }
            else if (position == 1)
            {
                title = "Withdrawal";
            }

            return title;
        }
    }



}
