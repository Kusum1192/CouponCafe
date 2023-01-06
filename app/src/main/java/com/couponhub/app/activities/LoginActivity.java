package com.couponhub.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.couponhub.app.R;
import com.couponhub.app.BuildConfig;
import com.couponhub.app.MainActivity;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.UserAppOpenModel;
import com.couponhub.app.models.UserRegisterModel;
import com.couponhub.app.models.UserSignIN;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, InstallReferrerStateListener {
/*if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O)*/
    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    String TAG = "testing_login";
    private TextView privacypolicy,termcondition;
    String authCode,idToken;
    String BASE_URL_WEB="https://couponhub.app/info-files/";
    String utm_source,utm_medium,utm_campaign,utm_term,utm_content, referrerUrl,myGid;
    private InstallReferrerClient mReferrerClient;
    private final Executor backgroundExecutor= Executors.newSingleThreadExecutor();
    private ReferrerDetails responseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getAdvertisingId();
        init();
        setUTM();
    }
    private void init() {
//        getSupportActionBar().setTitle("");
        TextView tv_google_login = findViewById(R.id.tv_google_login);
        TextView tv_fb_login = findViewById(R.id.tv_email_login);
        privacypolicy = findViewById(R.id.txt_privacy);
        termcondition = findViewById(R.id.txt_termcondition);
        privacypolicy.setOnClickListener(this);
        termcondition.setOnClickListener(this);
        tv_google_login.setOnClickListener(this);
        tv_fb_login.setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        FirebaseApp.initializeApp(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        getIdThread1();
    }
    public void getIdThread1() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
//                    advertisingId = advertId;
                    Constants.setSharedPreferenceString(LoginActivity.this,"adverId",advertId);
//                    Log.e("id", "getIdThread: "+advertId );
                }catch (Exception e){
                    e.printStackTrace();
                }
                return advertId;
            }
            @Override
            protected void onPostExecute(String advertId) {
//                Toast.makeText(getApplicationContext(), "advg: "+advertId, Toast.LENGTH_SHORT).show();
            }
        };
        task.execute();
    }

    @Override
    protected void onActivityResult( final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "onActivityResult: "+task.getResult().getAccount());
                authCode = account.getServerAuthCode();
                idToken = account.getIdToken();
                Log.e(TAG, "onActivityResult:authCode "+authCode +" ::idToken:: "+idToken);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success:idtoken "+acct.getIdToken());
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(LoginActivity.this, "token:: "+mAuth.getAccessToken(true), Toast.LENGTH_SHORT).show();
                            String apilevel  = String.valueOf(android.os.Build.VERSION.SDK_INT);
                            String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
                            String devicename =  android.os.Build.MODEL;
                            String version ="";
                            int versioncode = 0;
                            try {
                                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                version = pInfo.versionName;
                                versioncode = pInfo.versionCode;
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            Constants.setSharedPreferenceString(LoginActivity.this,"username",user.getDisplayName());
                            Constants.setSharedPreferenceString(LoginActivity.this,"useremail",user.getEmail());
                            Constants.setSharedPreferenceInt(LoginActivity.this,"versionCode",versioncode);
                            Constants.setSharedPreferenceString(LoginActivity.this,"versionName",version);
                            userSignUp(apilevel,android_id,devicename,"google", acct.getId(),acct.getEmail(),acct.getDisplayName(),
                                    acct.getPhotoUrl(), Constants.getSharedPreferenceString(LoginActivity.this,"adverId","")
                                    ,version,versioncode);
                        }
                        else {
                        }
                    }
                });
    }
    private void userSignUp(String apilevel, String android_id, String devicename, String socialtype, String id, String email,
                            String displayName, Uri photoUrl, String adId, String version, int versioncode) {

        if(!((Activity) LoginActivity.this).isFinishing()){
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<UserRegisterModel> call = apiService.userSignUp(apilevel,android_id,devicename,socialtype,id,email,displayName,photoUrl,
                adId,version,versioncode,Constants.getSharedPreferenceString(LoginActivity.this,"token","")
                ,utm_source,utm_medium,utm_term,utm_content,utm_campaign
              /* Constants.getSharedPreferenceString(LoginActivity.this,"utm_source",""),
                Constants.getSharedPreferenceString(LoginActivity.this,"utm_medium",""),
                Constants.getSharedPreferenceString(LoginActivity.this,"utm_term",""),
                Constants.getSharedPreferenceString(LoginActivity.this,"utm_content",""),
                Constants.getSharedPreferenceString(LoginActivity.this,"utm_campaign","")*/
        );

        call.enqueue(new Callback<UserRegisterModel>() {
            @Override
            public void onResponse(@NonNull Call<UserRegisterModel> call, Response<UserRegisterModel> response) {
                progressDialog.dismiss();
                try{
                   if(response!=null){
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()==200) {
                                Constants.setSharedPreferenceInt(LoginActivity.this, "userId", response.body().getUserId());
                                Constants.setSharedPreferenceString(LoginActivity.this, "username", response.body().getSocialName());
                                Constants.setSharedPreferenceString(LoginActivity.this, "userimage", response.body().getSocialImgurl());
                                Constants.setSharedPreferenceString(LoginActivity.this, "securitytoken", response.body().getSecurityToken());
                                openApp();
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }else Toast.makeText(LoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UserRegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, getString(R.string.systemmessage)+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openApp(){
        int versionCode = BuildConfig.VERSION_CODE;
        final String versionName = BuildConfig.VERSION_NAME;
        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<UserAppOpenModel> call = apiService.appOpen(Constants.getSharedPreferenceInt(LoginActivity.this,"userId",0),
                Constants.getSharedPreferenceString(LoginActivity.this,"securitytoken",""),versionName,versionCode);

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
                            Constants.setSharedPreferenceString(LoginActivity.this,"userFrom",userFrom);
                            Constants.setSharedPreferenceString(LoginActivity.this,"forceUpdatePackage",packAge);
                            Constants.setSharedPreferenceInt(LoginActivity.this, "userAmount", amount);
                            Constants.setSharedPreferenceString(LoginActivity.this, "totalcoins", coins);
                            Constants.setSharedPreferenceString(LoginActivity.this, "curency", curency);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(LoginActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserAppOpenModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this,getString(R.string.systemmessage)+t,Toast.LENGTH_SHORT).show();
            }
        });
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }

    // [END signin]
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_google_login:
                signIn();
                break;

            case R.id.tv_email_login:
                Intent intent=new Intent(this,SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.txt_privacy:
                String url = BASE_URL_WEB+"privacy-policy.html";
                webViewLoad(url,"Privacy Policy");
                break;

            case R.id.txt_termcondition:
                String urlcondition = BASE_URL_WEB+"terms-conditions.html";
                webViewLoad(urlcondition,"Terms Of Services");
                break;
        }
    }
    private void webViewLoad(String url,String title){
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle(title);

        WebView wv = new WebView(LoginActivity.this);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        alert.setView(wv);
        alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
    public void getAdvertisingId() {
        AsyncTask.execute(() -> {
            try {
                AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(LoginActivity.this);
                myGid = adInfo != null ? adInfo.getId() : null;
                Log.i("UIDMY", myGid);
            } catch (Exception e) {
                Log.e("error", e.toString());
            }
        });
    }
    private void setUTM() {
        mReferrerClient = InstallReferrerClient.newBuilder(this).build();
        mReferrerClient.startConnection(LoginActivity.this);
        checkInstallReferrer();
    }

    public void onInstallReferrerSetupFinished(int responseCode) {
        switch (responseCode) {
            case InstallReferrerClient.InstallReferrerResponse.OK:
                // Connection established

                try {
                    getReferralUser();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                // API not available on the current Play Store app
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                // Connection could not be established
                break;
            case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR:
                break;
            case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED:
                break;
        }
    }
    public void onInstallReferrerServiceDisconnected() {

    }

    private void getReferralUser() throws RemoteException {
        ReferrerDetails response = mReferrerClient.getInstallReferrer();
        String referrerData = response.getInstallReferrer();
        Log.e("TAG", "Install referrer:" + response.getInstallReferrer());
        // for utm terms
        HashMap<String, String> values = new HashMap<>();
        //if (values.containsKey("utm_medium") && values.containsKey("utm_term")) {
        try {
            if (referrerData != null) {
                String[] referrers = referrerData.split("&");
                Log.d(TAG, "getReferralUser: " + referrerData);
                for (String referrerValue : referrers) {
                    String[] keyValue = referrerValue.split("=");
                    values.put(URLDecoder.decode(keyValue[0], "UTF-8"), URLDecoder.decode(keyValue[1], "UTF-8"));
                }
                Log.e("TAG", "UTM medium:" + values.get("utm_medium"));
                Log.e("TAG", "UTM term:" + values.get("utm_term"));
                utm_medium = values.get("utm_medium");
                utm_term = values.get("utm_term");
                utm_source = values.get("utm_source");
                utm_campaign = values.get("utm_campaign");
                utm_content = values.get("utm_content");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkInstallReferrer() {
        InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(this).build();
        backgroundExecutor.execute(() -> getInstallReferrerFromClient(referrerClient));
    }
    private void getInstallReferrerFromClient(InstallReferrerClient referrerClient) {
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:

                        try {
                            responseRef = referrerClient.getInstallReferrer();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                        referrerUrl = responseRef.getInstallReferrer();
                        // Toast.makeText(LoginActivity.this, ""+responseRef.getInstallReferrer(), Toast.LENGTH_SHORT).show();
                        // TODO: If you're using GTM, call trackInstallReferrerforGTM instead.
                        // trackInstallReferrer(referrerUrl);
                        // Only check this once.
                        // getPreferences(MODE_PRIVATE).edit().putBoolean(prefInstallReferrer, true).commit();
                        // End the connection
                        referrerClient.endConnection();
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }
            @Override
            public void onInstallReferrerServiceDisconnected() {
            }
        });
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
