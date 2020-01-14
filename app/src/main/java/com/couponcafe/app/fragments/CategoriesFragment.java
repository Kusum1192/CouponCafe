package com.couponcafe.app.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.midi.MidiOutputPort;
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
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.interfaces.ParentListItem;
import com.couponcafe.app.models.CategoriesModel;
import com.couponcafe.app.models.CategoryDatum;
import com.couponcafe.app.models.SubCategory;
import com.couponcafe.app.testing.ExpandableRecyclerViewAdapter;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;
import com.couponcafe.app.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment {


    MovieCategoryAdapter mAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String TAG = "testing";

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

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);

      getcategoryData();

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

                            ArrayList<CategoryDatum> categoriesModel = response.body().getCategoryData();


                            ExpandableRecyclerViewAdapter expandableCategoryRecyclerViewAdapter =
                                    new ExpandableRecyclerViewAdapter(getActivity(), categoriesModel);

                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(expandableCategoryRecyclerViewAdapter);


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
