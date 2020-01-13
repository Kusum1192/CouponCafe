package com.couponcafe.app.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.ExpandableRecyclerAdapter;
import com.couponcafe.app.adapter.MovieCategory;
import com.couponcafe.app.adapter.MovieCategoryAdapter;
import com.couponcafe.app.adapter.Movies;
import com.couponcafe.app.adapter.RecyclerTouchListener;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.CategoriesModel;
import com.couponcafe.app.models.CategoryDatum;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment {


    private MovieCategoryAdapter mAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    public CategoriesFragment() {
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
      View view = inflater.inflate(R.layout.categories_fragment, container, false);


        Movies movie_one = new Movies("Clothing","offers");
        Movies  movie_two  = new Movies("Footwear","offers");
        Movies  movie_three = new Movies("Bags & Accessories","offers");
        Movies movie_four  = new Movies("Watch & Sunglasses","offers");
        Movies movie_five = new Movies("Flight","offers");
        Movies movie_six = new Movies("Hotel","offers");
        Movies movie_seven = new Movies("Bus","offers");
        Movies movie_eight = new Movies("Cabs","offers");
        Movies movie_nine = new Movies("Food Ordering","offers");
        Movies movie_ten = new Movies("Drinks & Beverages","offers");
        Movies movie_eleven = new Movies("Pizza","offers");
        Movies movie_tweleve = new Movies("Grocery","offers");
        Movies movie_thirteen = new Movies("Personal Care & Beauty","offers");
        Movies movie_forteen = new Movies("Makeup & Cosmetics","offers");
        Movies movie_fifteen = new Movies("Medicines & Health","offers");
        Movies movie_sixteen = new Movies("Nutrition","offers");
        Movies movie_one_seven = new Movies("Mobile Recharge","offers");
        Movies movie_one_eight = new Movies("Bill Payments","offers");
        Movies movie_one_nine = new Movies("DTH","offers");


        MovieCategory molvie_category_one = new MovieCategory("Fashion","offers", Arrays.asList(movie_one, movie_two, movie_three, movie_four));
        MovieCategory molvie_category_two = new MovieCategory("Travel","offers", Arrays.asList(movie_five, movie_six, movie_seven,movie_eight));
        MovieCategory molvie_category_three = new MovieCategory("Food & Dining","offers", Arrays.asList(movie_nine, movie_ten, movie_eleven,movie_tweleve));
        MovieCategory molvie_category_four = new MovieCategory("Beauty & Health","offers", Arrays.asList(movie_thirteen, movie_forteen, movie_fifteen,movie_sixteen));
        MovieCategory molvie_category_five = new MovieCategory("Recharges", "offer",Arrays.asList(movie_one_seven, movie_one_eight, movie_one_nine));

        final List<MovieCategory> movieCategories = Arrays.asList(molvie_category_one,  molvie_category_two, molvie_category_three,molvie_category_four,molvie_category_five);


        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        mAdapter = new MovieCategoryAdapter(getActivity(), movieCategories);


//        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
//            @Override
//            public void onListItemExpanded(int position) {
//                MovieCategory expandedMovieCategory = movieCategories.get(position);
//                String toastMsg = getResources().getString(R.string.expanded, expandedMovieCategory.getName());
//               // Toast.makeText(getActivity(),"child:expends "+movieCategories,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onListItemCollapsed(int position) {
//                MovieCategory collapsedMovieCategory = movieCategories.get(position);
//
//                String toastMsg = getResources().getString(R.string.collapsed, collapsedMovieCategory.getName());
//                //Toast.makeText(getActivity(),toastMsg,Toast.LENGTH_SHORT).show();
//               // Toast.makeText(getActivity(),"child:collapsed "+movieCategories.get(position).getChildItemList().get(position),Toast.LENGTH_SHORT).show();
//            }
//        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    private void getcategoryData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<CategoriesModel> call = apiService.getAllCategories(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
                Constants.getSharedPreferenceString(getActivity(),"securitytoken",""),
                Constants.getSharedPreferenceString(getActivity(),"versionName",""),
                Constants.getSharedPreferenceInt(getActivity(),"versionCode",0));

        if(!((Activity) getActivity()).isFinishing()) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel>call, Response<CategoriesModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){

                           ArrayList<CategoryDatum>categoriesModel = response.body().getCategoryData();


                           // mAdapter = new MovieCategoryAdapter(getActivity(), categoriesModel);


//                            todayBestAdapter = new TodayBestOfferListAdapter(bestOfferDatalist,getActivity());
//                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
//                            recycler_view_best_offers.setLayoutManager(mLayoutManager1);
//                            recycler_view_best_offers.setAdapter(todayBestAdapter);

//                            recycler_view_best_offers.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recycler_view_best_offers, new RecyclerTouchListener.ClickListener() {
//                                @Override
//                                public void onClick(View view, int position) {
//
//                                }
//
//                                @Override
//                                public void onLongClick(View view, int position) {
//
//                                }
//                            }));



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
            public void onFailure(Call<CategoriesModel>call, Throwable t) {
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
