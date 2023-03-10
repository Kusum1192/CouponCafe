package com.couponhub.app.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.couponhub.app.BuildConfig;
import com.couponhub.app.R;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.CategoriesModel;
import com.couponhub.app.models.CategoryDatum;
import com.couponhub.app.testing.ExpandableRecyclerViewAdapter;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String TAG = "testing";
    protected FragmentActivity mActivity;
    SwipeRefreshLayout refreshLayout;
    ExpandableRecyclerViewAdapter expandableCategoryRecyclerViewAdapter;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        getcategoryData();

        return view;
    }


    private void getcategoryData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<CategoriesModel> call = apiService.getAllCategories(Constants.getSharedPreferenceInt(mActivity, "userId", 0),
                Constants.getSharedPreferenceString(mActivity, "securitytoken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        if (!((Activity) mActivity).isFinishing()) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            ArrayList<CategoryDatum> categoriesModel = response.body().getCategoryData();

                            expandableCategoryRecyclerViewAdapter = new ExpandableRecyclerViewAdapter(mActivity, categoriesModel);

                            recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                            recyclerView.setAdapter(expandableCategoryRecyclerViewAdapter);
                            recyclerView.setNestedScrollingEnabled(false);


                        } else {
                            Toast.makeText(mActivity, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(mActivity, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
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
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }

    }


    @Override
    public void onRefresh() {
        getcategoryData();
        refreshLayout.setRefreshing(false);
    }
}
