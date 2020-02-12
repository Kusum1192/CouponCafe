package com.couponcafe.app.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.couponcafe.app.R;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.ProfileDataModel;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.CircleTransform;
import com.couponcafe.app.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
   // ViewPager viewPager;
    TabLayout tabLayout;

    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ProgressDialog progressDialog;
    ImageView imageView_profile;
    TextView tv_useremail,tv_username,tv_total;
    protected FragmentActivity mActivity;
    SwipeRefreshLayout refreshLayout;

    //Context context;
    View view;


    public ProfileFragment() {
        // Required empty public constructor
       // Toast.makeText(mActivity, "profile call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);

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
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getProfileData();
        refreshLayout.setRefreshing(false);
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

                            Picasso.get().load(response.body().getSocialImgurl())
                                    .placeholder(R.drawable.ic_placeholder_small)
                                    .error(R.drawable.ic_placeholder_small).transform(new CircleTransform())
                                    .into((imageView_profile));

                            tv_useremail.setText(response.body().getSocialEmail());
                            tv_username.setText(response.body().getSocialName());
                            tv_total.setText(Constants.getSharedPreferenceString(mActivity,"currency","")+""+response.body().getUserSavings());

                            fragment = new OverviewFragment(response.body().getUserAmount(),response.body().getPendingAmount(),response.body().getProducts());
                            fragmentManager = mActivity.getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, fragment);
                            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            fragmentTransaction.commit();

                            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    // Fragment fragment = null;
                                    switch (tab.getPosition()) {
                                        case 0:
                                            fragment = new OverviewFragment(response.body().getUserAmount(),response.body().getPendingAmount(),response.body().getProducts());
                                            break;
                                        case 1:
                                            fragment = new WithdrawalsFragment(response.body().getUserAmount(),response.body().getPendingAmount(),response.body().getMinPayLimit());
                                            break;

                                    }
                                    FragmentManager fm = mActivity.getSupportFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.frameLayout, fragment);
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                    ft.commit();
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });


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



}
