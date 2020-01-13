package com.couponcafe.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.RecyclerTouchListener;
import com.couponcafe.app.adapter.ViewTopStoreListAdapter;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.TopStoreDatum;
import com.couponcafe.app.models.ViewTopStoreModel;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllTopOffersActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardview_top_store;
    ProgressDialog progressDialog;
    RecyclerView recylerview_topstore;
    ViewTopStoreListAdapter viewTopStoreListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_top_offers);


        getSupportActionBar().setTitle("Top Stores");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getTopOffersData();



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_top_store:
                //Intent intent_topstore_detials = new Intent(ViewAllTopOffersActivity.this,TopStoresDetailsActivity.class);
              //  startActivity(intent_topstore_detials);
                break;
        }
    }

    private void getTopOffersData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ViewTopStoreModel> call = apiService.viewTopStore(Constants.getSharedPreferenceInt(ViewAllTopOffersActivity.this,"userId",0),
                Constants.getSharedPreferenceString(ViewAllTopOffersActivity.this,"securitytoken",""),
                Constants.getSharedPreferenceString(ViewAllTopOffersActivity.this,"versionName",""),
                Constants.getSharedPreferenceInt(ViewAllTopOffersActivity.this,"versionCode",0));

        if(!((Activity) ViewAllTopOffersActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(ViewAllTopOffersActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<ViewTopStoreModel>() {
            @Override
            public void onResponse(Call<ViewTopStoreModel>call, Response<ViewTopStoreModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){
                            final ArrayList<TopStoreDatum> topStoreDatumArrayList = response.body().getTopStoreData();

                            cardview_top_store  = findViewById(R.id.cardview_top_store);
                            recylerview_topstore  = findViewById(R.id.recylerview_topstore);
                            viewTopStoreListAdapter = new ViewTopStoreListAdapter(topStoreDatumArrayList,ViewAllTopOffersActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewAllTopOffersActivity.this);
                            recylerview_topstore.setLayoutManager(mLayoutManager);
                            recylerview_topstore.setAdapter(viewTopStoreListAdapter);

                            recylerview_topstore.addOnItemTouchListener(new RecyclerTouchListener(ViewAllTopOffersActivity.this, recylerview_topstore, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    TopStoreDatum topStores = topStoreDatumArrayList.get(position);
                                    Intent intent = new Intent(ViewAllTopOffersActivity.this, TopStoresDetailsActivity.class);
                                    intent.putExtra("storeId",topStores.getStoreId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));



                        }else{
                            Toast.makeText(ViewAllTopOffersActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(ViewAllTopOffersActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ViewTopStoreModel>call, Throwable t) {
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
