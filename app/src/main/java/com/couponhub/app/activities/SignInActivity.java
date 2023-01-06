package com.couponhub.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.couponhub.app.BuildConfig;
import com.couponhub.app.MainActivity;
import com.couponhub.app.R;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.UserAppOpenModel;
import com.couponhub.app.models.UserSignIN;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    /*private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +           // no white spaces
            ".{2,}" +               // at least 4 characters
            "$");*/
    TextInputEditText email_et, password_et;
    ProgressBar bar;
    private String emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email_et = findViewById(R.id.editEmailid);
        password_et = findViewById(R.id.editPassword);
        bar=findViewById(R.id.progressBar);
        Button register = findViewById(R.id.regbtn);
        register.setOnClickListener(v -> {
            emailInput = email_et.getText().toString().trim();
            String passwordInput = password_et.getText().toString().trim();// if password field is empty
           if (emailInput.isEmpty()) {
                email_et.setError("Field can't be empty");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email_et.setError("Input Correct Email");
            } else if (passwordInput.isEmpty()) {
                password_et.setError("Field can not be empty");
            } else if (passwordInput.length() <6) {
                password_et.setError("Password is too short");
           /* } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {// if password does not matches to the pattern
                password_et.setError("Password is too weak");// it will display an error message "Password is too weak"*/
            } else
                postData(emailInput, passwordInput);
        });
    }

    private void postData(String emailInput, String passwordInput) {
        bar.setVisibility(View.VISIBLE);
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<UserSignIN> call = apiService.userSignIn(emailInput,passwordInput, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        call.enqueue(new Callback<UserSignIN>() {
            @Override
            public void onResponse(Call<UserSignIN> call, Response<UserSignIN> response) {
                bar.setVisibility(View.INVISIBLE);
                try {

                    if (response.body()!=null){
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                Constants.setSharedPreferenceInt(SignInActivity.this, "flag", 1);
                                Constants.setSharedPreferenceInt(SignInActivity.this, "userId", (int) response.body().getUserId());
                                Constants.setSharedPreferenceString(SignInActivity.this, "securitytoken", response.body().getSecurityToken());
                                Constants.setSharedPreferenceString(SignInActivity.this, "username", "");
                                Constants.setSharedPreferenceString(SignInActivity.this, "useremail", "");

                                openApp();
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserSignIN> call, @NonNull Throwable t) {
                Toast.makeText(SignInActivity.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void openApp(){
        int versionCode = BuildConfig.VERSION_CODE;
        final String versionName = BuildConfig.VERSION_NAME;
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<UserAppOpenModel> call = apiService.appOpen(Constants.getSharedPreferenceInt(SignInActivity.this,"userId",0),
                Constants.getSharedPreferenceString(SignInActivity.this,"securitytoken",""),versionName,versionCode);

        call.enqueue(new Callback<UserAppOpenModel>() {
            @Override
            public void onResponse(Call<UserAppOpenModel> call, Response<UserAppOpenModel> response) {
                try{
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200) {
                            int amount = response.body().getUserAmount();
                            String coins = String.valueOf(response.body().getUserCoin());
                            String curency = String.valueOf(response.body().getCurrency());
                            String packAge = response.body().getPackAge();
                            String userFrom = "couponhub";
                            Constants.setSharedPreferenceString(SignInActivity.this,"userFrom",userFrom);
                            Constants.setSharedPreferenceString(SignInActivity.this,"forceUpdatePackage",packAge);
                            Constants.setSharedPreferenceInt(SignInActivity.this, "userAmount", amount);
                            Constants.setSharedPreferenceString(SignInActivity.this, "totalcoins", coins);
                            Constants.setSharedPreferenceString(SignInActivity.this, "curency", curency);

                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(SignInActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignInActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserAppOpenModel> call, Throwable t) {
                Toast.makeText(SignInActivity.this,getString(R.string.systemmessage)+t,Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
