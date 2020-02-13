package com.couponcafe.app.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.couponcafe.app.MainActivity;
import com.couponcafe.app.R;

import com.couponcafe.app.activities.CategoriesDetailsActivity;
import com.couponcafe.app.activities.OffersDetailsActivity;
import com.couponcafe.app.activities.TopStoresDetailsActivity;
import com.couponcafe.app.activities.ViewAllTopOffersActivity;
import com.couponcafe.app.adapter.TodayBestOfferListAdapter;

import com.couponcafe.app.adapter.TopStoresAdapter;
import com.couponcafe.app.adapter.RecyclerTouchListener;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.interfaces.refreshLayout;
import com.couponcafe.app.models.AllOffersDataModel;
import com.couponcafe.app.models.BestOfferDatum;
import com.couponcafe.app.models.SliderDatum;

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


public class HomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    String TAG = "testing";
    SwipeRefreshLayout refreshLayout;
    ArrayList<TopStoreDatum> topStoreDatalist;

    protected FragmentActivity mActivity;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        //setHasOptionsMenu(true);
        mPager = (ViewPager) root.findViewById(R.id.pager);
        cardview_share_invite = root.findViewById(R.id.cardview_share_invite);
        indicator = (CircleIndicator) root.findViewById(R.id.indicator);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);

        recylerview_topstore = root.findViewById(R.id.recylerview_topstore);
        recycler_view_best_offers = root.findViewById(R.id.recycler_view_best_offers);
        tv_viewtop_offers = root.findViewById(R.id.tv_viewtop_offers);
        tv_home_invite_image = root.findViewById(R.id.tv_home_invite_image);
        tv_view_all = root.findViewById(R.id.tv_view_all);
        tv_viewtop_offers.setOnClickListener(this);
        tv_view_all.setOnClickListener(this);
        cardview_share_invite.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(this);

        getAllOffersData();


        return root;
    }

//    @Override
//    public void onRefresh() {
//        getAllOffersData();
//        refreshLayout.setRefreshing(false);
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_share_invite:
                ((MainActivity)mActivity).setupBottomNavigationFrom(R.id.navigation_invite);
                break;

            case R.id.tv_viewtop_offers:
                Intent intent_topoffers = new Intent(mActivity, ViewAllTopOffersActivity.class);
                startActivity(intent_topoffers);
                break;

            case R.id.tv_view_all:
                Intent intent_best_offers = new Intent(mActivity, CategoriesDetailsActivity.class);
                startActivity(intent_best_offers);
                break;

        }
    }



    private void getAllOffersData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<AllOffersDataModel> call = apiService.getOffers(Constants.getSharedPreferenceInt(mActivity,"userId",0),
                Constants.getSharedPreferenceString(mActivity,"securitytoken",""),
                Constants.getSharedPreferenceString(mActivity,"versionName",""),
                Constants.getSharedPreferenceInt(mActivity,"versionCode",0));

        if(!((Activity) mActivity).isFinishing()) {
            progressDialog = new ProgressDialog(mActivity);
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
                            if(topStoreDatalist!=null){
                                topStoreDatalist.clear();
                            }

                           Constants.setSharedPreferenceInt(mActivity,"userAmount",response.body().getUserAmount());
                           Constants.setSharedPreferenceString(mActivity,"currency",response.body().getCurrency());
                           final ArrayList<SliderDatum> sliderArrayList = response.body().getSliderData();
                           final ArrayList<BestOfferDatum> bestOfferDatalist = response.body().getBestOfferData();
                           topStoreDatalist = response.body().getTopStoreData();

                            Picasso.get().load(response.body().getInviteImgurl())
                                    .placeholder(R.drawable.ic_placeholder_small)
                                    .error(R.drawable.ic_placeholder_small)
                                    .into((tv_home_invite_image));

                           // Toast.makeText(mActivity, ""+bestOfferData.size(), Toast.LENGTH_SHORT).show();
                            mPager.setAdapter(new Sliding_Adapter_For_viewpager_main(mActivity, sliderArrayList));
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
                            final Handler  handler = new Handler();
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
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                            recylerview_topstore.setLayoutManager(mLayoutManager);
                            recylerview_topstore.setAdapter(mAdapter);
                            recylerview_topstore.setNestedScrollingEnabled(false);
                            recylerview_topstore.addOnItemTouchListener(new RecyclerTouchListener(mActivity, recylerview_topstore, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    TopStoreDatum topStores = topStoreDatalist.get(position);
                                    Intent intent = new Intent(mActivity, TopStoresDetailsActivity.class);
                                    intent.putExtra("storeId",topStores.getStoreId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));

                            todayBestAdapter = new TodayBestOfferListAdapter(bestOfferDatalist,mActivity);
                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mActivity);
                            recycler_view_best_offers.setLayoutManager(mLayoutManager1);
                            recycler_view_best_offers.setAdapter(todayBestAdapter);
                            recycler_view_best_offers.setNestedScrollingEnabled(false);
                            recycler_view_best_offers.addOnItemTouchListener(new RecyclerTouchListener(mActivity, recycler_view_best_offers, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    BestOfferDatum bestOfferDatum = bestOfferDatalist.get(position);
                                    // Toast.makeText(mActivity, topStores.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mActivity, OffersDetailsActivity.class);
                                    intent.putExtra("offerId",bestOfferDatum.getOfferId());
//                                    Log.e(TAG, "onClick:bestOfferDatum.getOfferId() "+bestOfferDatum.getOfferId() );
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));

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

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;
        }

    }

    @Override
    public void onRefresh() {

        refreshLayout.setRefreshing(false);

    }


    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.share_main_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);

//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //MenuItem searchItem = menu.findItem(R.id.action_search);
       // SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                todayBestAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });

//    }




}