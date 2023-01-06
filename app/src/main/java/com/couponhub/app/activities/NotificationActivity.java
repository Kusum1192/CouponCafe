package com.couponhub.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.couponhub.app.BuildConfig;
import com.couponhub.app.R;
import com.couponhub.app.adapter.NotificationsListAdapter;
import com.couponhub.app.adapter.RecyclerTouchListener;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.CustomNotifyModel;
import com.couponhub.app.models.Notification;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerview_notification;

    NotificationsListAdapter notificationsListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setTitle(getString(R.string.title_notification));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getNotificationData();

    }


    private void getNotificationData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<CustomNotifyModel> call = apiService.getnotificationData(Constants.getSharedPreferenceInt(NotificationActivity.this,"userId",0),
                Constants.getSharedPreferenceString(NotificationActivity.this,"securitytoken",""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        if(!((Activity) NotificationActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(NotificationActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<CustomNotifyModel>() {
            @Override
            public void onResponse(Call<CustomNotifyModel>call, Response<CustomNotifyModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){
                            final ArrayList<Notification> notificationArrayList = response.body().getNotifications();

                            recyclerview_notification  = findViewById(R.id.recyclerview_notification);
                            notificationsListAdapter = new NotificationsListAdapter(notificationArrayList,NotificationActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NotificationActivity.this);
                            recyclerview_notification.setLayoutManager(mLayoutManager);
                            recyclerview_notification.setAdapter(notificationsListAdapter);

                            recyclerview_notification.addOnItemTouchListener(new RecyclerTouchListener(NotificationActivity.this, recyclerview_notification, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Notification notification = notificationArrayList.get(position);
                                    Intent intent = new Intent(NotificationActivity.this, OffersDetailsActivity.class);
                                    intent.putExtra("offerId",notification.getOfferId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));



                        }else{
                            Toast.makeText(NotificationActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(NotificationActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<CustomNotifyModel>call, Throwable t) {
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
