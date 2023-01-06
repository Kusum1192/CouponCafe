package com.couponhub.app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.couponhub.app.BuildConfig;
import com.couponhub.app.R;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.PayoutDataModel;
import com.couponhub.app.models.WalletRedeemModel;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentScreenActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    EditText et_paytm_mobile,email;
    TextView tv_submit;
    String  itemPrice;
    AppCompatSpinner spinner;
    String TAG = "testing";
    String paymode="Paytm";
    int thresoldValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);

        getSupportActionBar().setTitle(getString(R.string.withdrawal));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();


        getPayoutData();
    }

    private void initView() {
        et_paytm_mobile = findViewById(R.id.et_paytm_mobile);
        email = findViewById(R.id.et_email);
        spinner = findViewById(R.id.spiner_amount);
        tv_submit = findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getPayoutData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<PayoutDataModel> call = apiService.getpayoutData(Constants.getSharedPreferenceInt(PaymentScreenActivity.this,"userId",0),
                Constants.getSharedPreferenceString(PaymentScreenActivity.this,"securitytoken",""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        if(!((Activity) PaymentScreenActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(PaymentScreenActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<PayoutDataModel>() {
            @Override
            public void onResponse(Call<PayoutDataModel>call, Response<PayoutDataModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){

                            thresoldValue = response.body().getMinPayLimit();
                            String tranTxt = response.body().getTransText();
                            final String mimeType = "text/html";
                            final String encoding = "UTF-8";
                            String html = tranTxt;
                            WebView webView = (WebView) findViewById(R.id.WebView);
                            webView.loadData(html, mimeType, encoding);
                            ArrayList<String> ara = response.body().getPayoutValues();

                            int amount = response.body().getUserAmount();
                            Constants.setSharedPreferenceInt(PaymentScreenActivity.this,"userAmount",amount);

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PaymentScreenActivity.this, R.layout.simple_spinner_item, ara);
                            // Drop down layout style - list view with radio button
                            dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            // attaching data adapter to spinner
                            spinner.setAdapter(dataAdapter);


                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                   itemPrice = adapterView.getItemAtPosition(i).toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });



                        }else{
                            Toast.makeText(PaymentScreenActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(PaymentScreenActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PayoutDataModel>call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });


    }

    private void walletRedeemData(String email,String mobile,String payoutAmount,String paymode) {

//        Log.e(TAG, "walletRedeemData: "+email+" mobile: "+mobile+" coin: "+payoutAmount);

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<WalletRedeemModel> call = apiService.walletDataRedeem(Constants.getSharedPreferenceInt(PaymentScreenActivity.this,"userId",0),
                        Constants.getSharedPreferenceString(PaymentScreenActivity.this,"securitytoken",""),
                        mobile,payoutAmount,email,paymode,
                        BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        if(!((Activity) PaymentScreenActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(PaymentScreenActivity.this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<WalletRedeemModel>() {
            @Override
            public void onResponse(Call<WalletRedeemModel> call, Response<WalletRedeemModel> response) {
                dismissProgressDialog();

                if(response!=null){
                    if (response.isSuccessful()) {
                        if(response.body().getStatus()==200){

                            int amount = response.body().getUserAmount();
                            String showmsg = response.body().getShowText();

                            Log.e(TAG, "onResponse:amout "+amount );
                            Constants.setSharedPreferenceInt(PaymentScreenActivity.this,"userAmount",amount);

                            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentScreenActivity.this);
                            builder.setTitle("Withdraw Message");
                            builder.setMessage(showmsg);
                            builder.setPositiveButton("Ok",
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
                        else
                            Toast.makeText(PaymentScreenActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(PaymentScreenActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<WalletRedeemModel> call, Throwable t) {
                Log.e(TAG, "onFailure:fal "+t);
//                Toast.makeText(PaymentScreenActivity.this, getString(R.string.systemmessage) +t, Toast.LENGTH_SHORT).show();
            }

        } );

    }

    private void invalidateDataSpinner() {
        String emailaddress =  email.getText().toString();
        String moblienumber  = et_paytm_mobile.getText().toString();
        String spiner_selcted_amount   = itemPrice;

        if (moblienumber.isEmpty()) {
            et_paytm_mobile.setError("Paytm Mobile Required");
            et_paytm_mobile.requestFocus();
            return;
        }
        else if (emailaddress.isEmpty()) {
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        else if(spiner_selcted_amount.isEmpty() || spiner_selcted_amount.equals("Select Amount")){
            TextView errorText = (TextView)spinner.getSelectedView();
            errorText.setError("Select Amount");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            //errorText.setText("Select Amount");//changes the selected item text to this
//            Toast.makeText(this, "Select Amount", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!emailaddress.equals("") && !moblienumber.equals("") && !spiner_selcted_amount.equals("") ){
            try{

                int totalAmount = Constants.getSharedPreferenceInt(PaymentScreenActivity.this,"userAmount",0);

                int payoutAmount = Integer.parseInt(spiner_selcted_amount);

                if(thresoldValue <= totalAmount){

                    if(payoutAmount <= totalAmount){
                        walletRedeemData(emailaddress,moblienumber, String.valueOf(payoutAmount),paymode);
                    }
                    else {
                        Toast.makeText(this, "You don't have sufficient amount to withdraw.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Sorry! Minimum withdraw amount is "+ Constants.getSharedPreferenceString(PaymentScreenActivity.this, "curency", "")+thresoldValue, Toast.LENGTH_SHORT).show();
                }



            }catch (NumberFormatException e){
                Log.e("", "invalidateData: "+e );
                numberFormatException(e);

            }
        }

    }

    private void numberFormatException(NumberFormatException e){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PaymentScreenActivity.this);
        builder.setTitle("Number Format Exception");
        builder.setMessage(""+e);
        builder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });

        builder.show();
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
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.tv_submit:
                invalidateDataSpinner();
                break;
        }

    }
}
