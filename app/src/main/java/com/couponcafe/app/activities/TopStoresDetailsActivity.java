package com.couponcafe.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.RecyclerTouchListener;
import com.couponcafe.app.adapter.TopStoreDetailsAdapter;

import com.couponcafe.app.interfaces.APIService;

import com.couponcafe.app.models.OffersDatum;
import com.couponcafe.app.models.StoreDetails;
import com.couponcafe.app.models.TopStoreDetailsModel;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopStoresDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView recyclerview_topstores;
    ProgressDialog progressDialog;
    TopStoreDetailsAdapter topStoreDetailsAdapter;
    ImageView storeImage;
    TextView tv_activate_cashback;
    String action_url,TAG ="testing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topstore_details);
        try{
        tv_activate_cashback = findViewById(R.id.tv_activate_cashback);
        tv_activate_cashback.setOnClickListener(this);
        getSupportActionBar().setTitle("Tops Store Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            String storeId = String.valueOf(intent.getIntExtra("storeId", 0));
            getTopStoreDetials(storeId);
        }

        }catch (Exception ex){
          ex.printStackTrace();
            Log.e(TAG, "onCreate: "+ex );
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getTopStoreDetials(final String storeId) {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<TopStoreDetailsModel> call = apiService.getTopStoreDetial(storeId, Constants.getSharedPreferenceInt(TopStoresDetailsActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(TopStoresDetailsActivity.this, "securitytoken", ""),
                Constants.getSharedPreferenceString(TopStoresDetailsActivity.this, "versionName", ""),
                Constants.getSharedPreferenceInt(TopStoresDetailsActivity.this, "versionCode", 0));

        if (!((Activity) TopStoresDetailsActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(TopStoresDetailsActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<TopStoreDetailsModel>() {
            @Override
            public void onResponse(Call<TopStoreDetailsModel> call, Response<TopStoreDetailsModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            recyclerview_topstores = findViewById(R.id.recyclerview_topstores);
                            storeImage = findViewById(R.id.storeImage);

                            StoreDetails storeDetails = response.body().getStoreDetails();
                            final ArrayList<OffersDatum> storeDetailsArrayList = storeDetails.getOffersData();


                            Picasso.get().load(storeDetails.getImageUrl())
                                    .placeholder(R.drawable.ic_placeholder_small)
                                    .error(R.drawable.ic_placeholder_small)
                                    .into((storeImage));
                            TextView tv_topstore_name = findViewById(R.id.tv_topstore_name);
                            TextView tv_topstore_cashback = findViewById(R.id.tv_topstore_cashback);
                            tv_topstore_name.setText(storeDetails.getStoreName());
                            tv_topstore_cashback.setText(storeDetails.getCashBack());

                            action_url = storeDetails.getActionUrl();

                            topStoreDetailsAdapter = new TopStoreDetailsAdapter(storeDetailsArrayList, TopStoresDetailsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TopStoresDetailsActivity.this);
                            recyclerview_topstores.setLayoutManager(mLayoutManager);
                            recyclerview_topstores.setAdapter(topStoreDetailsAdapter);
                            recyclerview_topstores.setNestedScrollingEnabled(false);
                            recyclerview_topstores.addOnItemTouchListener(new RecyclerTouchListener(TopStoresDetailsActivity.this, recyclerview_topstores, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    OffersDatum offerId = storeDetailsArrayList.get(position);
                                    Intent intent = new Intent(TopStoresDetailsActivity.this, OffersDetailsActivity.class);
                                    intent.putExtra("offerId", offerId.getOfferId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));


                        } else {
                            Toast.makeText(TopStoresDetailsActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(TopStoresDetailsActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<TopStoreDetailsModel> call, Throwable t) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_main_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_activate_cashback:
                if(action_url!=null){
                    startWebview(action_url);
                }

                break;
        }

    }
    private void startWebview(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}
