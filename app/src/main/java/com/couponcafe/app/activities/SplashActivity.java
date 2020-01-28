package com.couponcafe.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.couponcafe.app.BuildConfig;
import com.couponcafe.app.MainActivity;
import com.couponcafe.app.R;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.UserAppOpenModel;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    String TAG ="testing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        TextView tv_version = findViewById(R.id.tv_version);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tv_version.setText("version: "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(isNetworkAvailable(SplashActivity.this)){
            if (Constants.getSharedPreferenceInt(SplashActivity.this, "userId", 0)!=0 &&
                    !Constants.getSharedPreferenceString(SplashActivity.this, "securitytoken", "").equals("null")) {

                if (getIntent().getExtras() != null) {
                    String title = null;
                    for (String key : getIntent().getExtras().keySet()) {
                        if (key.equals("OfferKey")) {
                            title = getIntent().getExtras().getString(key);
                        }
                    }
                    if(title!=null && !title.equals("null")){
                        Intent intentProductdDtails = new Intent(SplashActivity.this, OffersDetailsActivity.class);
                        intentProductdDtails.putExtra("offerId", Integer.parseInt(title));
                        startActivityForResult(intentProductdDtails,105);
                    }

                    else{
                        openApp();
                    }
                }
                else{
                    openApp();
                }

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!((Activity) SplashActivity.this).isFinishing()){
                            progressDialog = new ProgressDialog(SplashActivity.this,R.style.MyAlertDialogStyle);
                            progressDialog.setMessage(getString(R.string.loadingwait));
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                        }

                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                },3000);

            }
        }
        else{
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("Alert!");
            builder.setMessage("Please Check Your Internet Connection.");
            builder.setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.setCancelable(false);
            builder.show();
        }

    }

    public void openApp(){
        int versionCode = BuildConfig.VERSION_CODE;
        final String versionName = BuildConfig.VERSION_NAME;

        Constants.setSharedPreferenceInt(SplashActivity.this,"versionCode",versionCode);
        Constants.setSharedPreferenceString(SplashActivity.this,"versionName",versionName);
        Log.e(TAG, "onResponse:splash "+Constants.getSharedPreferenceString(SplashActivity.this, "securitytoken", ""));
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<UserAppOpenModel> call = apiService.appOpen(Constants.getSharedPreferenceInt(SplashActivity.this,"userId",0),
                Constants.getSharedPreferenceString(SplashActivity.this,"securitytoken",""),
                Constants.getSharedPreferenceString(SplashActivity.this,"versionName",""),
                Constants.getSharedPreferenceInt(SplashActivity.this,"versionCode",0));

        if(!((Activity) SplashActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(SplashActivity.this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }


        call.enqueue(new Callback<UserAppOpenModel>() {
            @Override
            public void onResponse(Call<UserAppOpenModel> call, Response<UserAppOpenModel> response) {

                dismissProgressDialog();
                try {
                    if(response!=null){
                        if (response.isSuccessful()) {
                            if (response.body().getForceUpdate()) {
                                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                                builder.setMessage("Your " + getString(R.string.app_name) + " seems very old, Please update to get new Earning features!!");
                                builder.setPositiveButton("UPDATE NOW",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();

                                                try {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Constants.getSharedPreferenceString(SplashActivity.this,"forceUpdatePackage",""))));
                                                } catch (ActivityNotFoundException e) {
                                                    // TODO: handle exception
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Constants.getSharedPreferenceString(SplashActivity.this,"forceUpdatePackage",""))));

                                                }
                                                finish();
                                            }
                                        });
                                builder.setCancelable(false);
                                builder.show();

                            } else {
                                if (response.body().getStatus() == 200) {
                                    String amount = String.valueOf(response.body().getUserAmount());
                                    String coins = String.valueOf(response.body().getUserCoin());
                                    String curency = String.valueOf(response.body().getCurrency());
                                    String packAge = response.body().getPackAge();

                                    String userFrom = "couponhub";
                                    Constants.setSharedPreferenceString(SplashActivity.this,"userFrom",userFrom);

                                    Constants.setSharedPreferenceString(SplashActivity.this,"forceUpdatePackage",packAge);
                                    Constants.setSharedPreferenceString(SplashActivity.this, "totalamount", amount);
                                    Constants.setSharedPreferenceString(SplashActivity.this, "totalcoins", coins);
                                    Constants.setSharedPreferenceString(SplashActivity.this, "curency", curency);

                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(SplashActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }
                    else Toast.makeText(SplashActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Log.e(TAG, "onResponse:110 "+e );
                }
            }

            @Override
            public void onFailure(Call<UserAppOpenModel> call, Throwable t) {
                Toast.makeText(SplashActivity.this,getString(R.string.systemmessage)+t,Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();

    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
