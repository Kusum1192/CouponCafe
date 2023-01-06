package com.couponhub.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.BuildConfig;
import com.couponhub.app.R;
import com.couponhub.app.adapter.UserTransactionsListAdapter;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.Transaction;
import com.couponhub.app.models.UserTransactionsModel;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTransactionsActivity extends AppCompatActivity {

    RecyclerView recyclerview_user_transaction;

    UserTransactionsListAdapter userTransactionsListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transaction);

        getSupportActionBar().setTitle(getString(R.string.user_transactions));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getUserTransactions();

    }


    private void getUserTransactions() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<UserTransactionsModel> call = apiService.getUserTransaction(Constants.getSharedPreferenceInt(UserTransactionsActivity.this,"userId",0),
                Constants.getSharedPreferenceString(UserTransactionsActivity.this,"securitytoken",""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        if(!((Activity) UserTransactionsActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(UserTransactionsActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<UserTransactionsModel>() {
            @Override
            public void onResponse(Call<UserTransactionsModel>call, Response<UserTransactionsModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){
                            final ArrayList<Transaction> userTransactionsModelArrayList = response.body().getTransactions();

                            recyclerview_user_transaction  = findViewById(R.id.recyclerview_user_transaction);
                            userTransactionsListAdapter = new UserTransactionsListAdapter(userTransactionsModelArrayList, UserTransactionsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UserTransactionsActivity.this);
                            recyclerview_user_transaction.setLayoutManager(mLayoutManager);
                            recyclerview_user_transaction.setAdapter(userTransactionsListAdapter);

                        }else{
                            Toast.makeText(UserTransactionsActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(UserTransactionsActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserTransactionsModel>call, Throwable t) {
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
}
