package com.couponcafe.app.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.couponcafe.app.MainActivity;
import com.couponcafe.app.R;
import com.couponcafe.app.activities.BestOffersActivity;
import com.couponcafe.app.activities.CategoriesDetailsActivity;
import com.couponcafe.app.activities.OffersDetailsActivity;
import com.couponcafe.app.activities.TopStoresDetailsActivity;
import com.couponcafe.app.activities.ViewAllTopOffersActivity;
import com.couponcafe.app.adapter.TodayBestOfferListAdapter;
import com.couponcafe.app.adapter.TopStores;
import com.couponcafe.app.adapter.TopStoresAdapter;
import com.couponcafe.app.adapter.RecyclerTouchListener;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.AllOffersDataModel;
import com.couponcafe.app.models.BestOfferDatum;
import com.couponcafe.app.models.SliderDatum;
import com.couponcafe.app.models.TopOfferDatum;
import com.couponcafe.app.models.TopStoreDatum;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;
import com.couponcafe.app.utils.Sliding_Adapter_For_viewpager_main;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private CircleIndicator indicator;
    CardView cardview_share_invite;
    RecyclerView recylerview_topstore,recycler_view_best_offers;
    private TopStoresAdapter mAdapter;
    private TodayBestOfferListAdapter todayBestAdapter;

    TextView tv_viewtop_offers,tv_view_all;
    ProgressDialog progressDialog;
    ImageView tv_home_invite_image;

    public HomeFragment() {
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        mPager = (ViewPager) root.findViewById(R.id.pager);
        cardview_share_invite = root.findViewById(R.id.cardview_share_invite);
        indicator = (CircleIndicator) root.findViewById(R.id.indicator);


        cardview_share_invite.setOnClickListener(this);
        recylerview_topstore = root.findViewById(R.id.recylerview_topstore);
        recycler_view_best_offers = root.findViewById(R.id.recycler_view_best_offers);
        tv_viewtop_offers = root.findViewById(R.id.tv_viewtop_offers);
        tv_home_invite_image = root.findViewById(R.id.tv_home_invite_image);
        tv_view_all = root.findViewById(R.id.tv_view_all);
        tv_viewtop_offers.setOnClickListener(this);
        tv_view_all.setOnClickListener(this);


        getAllOffersData();


        return root;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_share_invite:
                ((MainActivity)getActivity()).setupBottomNavigationFrom(R.id.navigation_invite);
                break;

            case R.id.tv_viewtop_offers:
                Intent intent_topoffers = new Intent(getActivity(), ViewAllTopOffersActivity.class);
                startActivity(intent_topoffers);
                break;

            case R.id.tv_view_all:
                Intent intent_best_offers = new Intent(getActivity(), CategoriesDetailsActivity.class);
                startActivity(intent_best_offers);
                break;

        }
    }



    private void getAllOffersData() {
        Constants.setSharedPreferenceInt(getActivity(),"userId",1);
        Constants.setSharedPreferenceString(getActivity(),"securitytoken","121212121");
        Constants.setSharedPreferenceString(getActivity(),"versionName","1.0");
        Constants.setSharedPreferenceInt(getActivity(),"versionCode",1);

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<AllOffersDataModel> call = apiService.getOffers(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
                Constants.getSharedPreferenceString(getActivity(),"securitytoken",""),
                Constants.getSharedPreferenceString(getActivity(),"versionName",""),
                Constants.getSharedPreferenceInt(getActivity(),"versionCode",0));

        if(!((Activity) getActivity()).isFinishing()) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<AllOffersDataModel>() {
            @Override
            public void onResponse(Call<AllOffersDataModel>call, Response<AllOffersDataModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){
                           Constants.setSharedPreferenceString(getActivity(),"userAmount",String.valueOf(response.body().getUserAmount()));
                           Constants.setSharedPreferenceString(getActivity(),"currency",response.body().getCurrency());
                           final ArrayList<SliderDatum> sliderArrayList = response.body().getSliderData();
                           final ArrayList<BestOfferDatum> bestOfferDatalist = response.body().getBestOfferData();
                           final ArrayList<TopStoreDatum> topStoreDatalist = response.body().getTopStoreData();

                            Picasso.get().load(response.body().getInviteImgurl())
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .into((tv_home_invite_image));

                           // Toast.makeText(getActivity(), ""+bestOfferData.size(), Toast.LENGTH_SHORT).show();
                            mPager.setAdapter(new Sliding_Adapter_For_viewpager_main(getActivity(), sliderArrayList));
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
                                    if (currentPage == sliderArrayList.size()) {
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
                            }, 2500, 7500);

                            mAdapter = new TopStoresAdapter(topStoreDatalist);
                            recylerview_topstore.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            recylerview_topstore.setLayoutManager(mLayoutManager);
                            recylerview_topstore.setAdapter(mAdapter);
                            recylerview_topstore.setNestedScrollingEnabled(false);
                            recylerview_topstore.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recylerview_topstore, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    TopStoreDatum topStores = topStoreDatalist.get(position);
                                    Intent intent = new Intent(getActivity(), TopStoresDetailsActivity.class);
                                    intent.putExtra("storeId",topStores.getStoreId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));

                            todayBestAdapter = new TodayBestOfferListAdapter(bestOfferDatalist,getActivity());
                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                            recycler_view_best_offers.setLayoutManager(mLayoutManager1);
                            recycler_view_best_offers.setAdapter(todayBestAdapter);
                            recycler_view_best_offers.setNestedScrollingEnabled(false);
                            recycler_view_best_offers.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recycler_view_best_offers, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    BestOfferDatum bestOfferDatum = bestOfferDatalist.get(position);
                                    // Toast.makeText(getActivity(), topStores.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), OffersDetailsActivity.class);
                                    intent.putExtra("offerId",bestOfferDatum.getOfferId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));



                        }else{
                            Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<AllOffersDataModel>call, Throwable t) {
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
}